<?php

namespace App\Controller;

use Swift_Mailer;

use Dompdf\Dompdf;
use Swift_Message;
use Dompdf\Options;
use DateTimeInterface;
use App\Entity\Reservation;
use App\Entity\Terrain;
use App\Form\ReservationType;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\ReservationRepository;
use Symfony\Component\HttpFoundation\Request;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\Json;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Exception\NotEncodableValueException;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Knp\Bundle\PaginatorBundle\Pagination\SlidingPaginationInterface;
use App\Entity\Rdv;
use App\Form\Rdv1Type;
use App\Repository\RdvRepository;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\BarChart;
use Symfony\Component\HttpFoundation\RequestStack;
use App\Services\QrcodeService;
use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\NotifierInterface;



use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

class RdvController extends AbstractController
{
    /**
     * @Route("/", name="app_rdv")
     */
    public function index(): Response
    {
        $rdv= $this->getDoctrine()->getManager()->getRepository(Rdv::class)->findAll();
        return $this->render('back/index.html.twig', [
           'r'=>$rdv
        ]);
    }


   
      /**
     * @Route("/affrdv", name="affrdv")
     */
    public function affrdv(): Response
    {
        $rdv= $this->getDoctrine()->getManager()->getRepository(Rdv::class)->findAll();
        return $this->render('rdv/rdv.html.twig', [
           'r'=>$rdv
        ]);
       
    }

   


    /**
     * @Route("/listrdvpdf", name="listrdvpdf")
     */
    public function listrdvpdf(Request $request)
    {

        $entityManager = $this->getDoctrine();
        $list = $entityManager->getRepository(Rdv::class)
            ->findAll();


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        //$pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $html=$this->render('rdv/listpdf.html.twig', [
            'listrdvpdf' => $list
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html.'l');

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("rdv/listpdf.html.twig", [
            "Attachment" => false
        ]);


    }

/**
     * @Route("visite/recherche",name="recherche")
     */
    function Recherche (VisiteRepository $rep,Request $request)
    {
        $data =$request->get('search');

        $visite=$rep->findBy(['idvisite'=>$data]);

        return $this->render('visite/visite.html.twig', [
            'r'=>$visite]);



    }


    

 /**
     * @Route("/back", name="back")
     */
    public function back(): Response
    {
        
        return $this->render('back/index.html.twig');
          
    }

     /**
     * @Route("/front", name="front")
     */
    public function front(): Response
    {
        
        return $this->render('front/index.html.twig');
          
    }
    /**
     * @Route("/calendar/ok", name="calendar", methods={"GET"})
     */
    public function calendar(RdvRepository $rdvRepository): Response
    {
        $events=$rdvRepository->findAll();
        $rdvs = [];

        foreach($events as $rdv){
            $rdvs[] = [

                'date' => $rdv->getDate()->format('Y-m-d H:i'),
               

                'title' => $rdv->getNompatient()
               

            ];
        }
       

        $data = json_encode($rdvs);
       

        return $this->render('rdv/calendar.html.twig',compact('data'));
    }


    
/**
     * @Route("/ajouterrdv", name="ajouterrdv")
     */
    public function ajouterrdv(Request $request,FlashyNotifier $flashy): Response
    {
       
       $rdv=new Rdv();
       $form=$this->createForm(Rdv1Type::class,$rdv);
       $form->handleRequest($request);
       if ($form->isSubmitted() && $form->isValid()) {
        $em=$this->getDoctrine()->getManager();
        $em->persist($rdv);//add
        $em->flush();
        $flashy->success('Article Ajoutée','http://your-awesome-link.com');
       
        return $this->render('front/confirmation.html.twig'
        
          
        );
   
     

        
    }
    return $this->render('rdv/createrdv.html.twig',['f'=>$form->createView()]);
}

/**
     * @Route("/supprdv/{idrdv}", name="supprdv")
     */
    public function supprdv(Rdv $rdv,Request $request, NotifierInterface $notifier ): Response
    {
        $em=$this->getDoctrine()->getManager();
        $form=$this->createForm(Rdv1Type::class,$rdv);
       $form->handleRequest($request);
       
        $mail = new PHPMailer(true);
           

        try {
            
            $time = date('H:i:s \O\n d/m/Y');
            $date = $form->get('date')->getData();
            $nom = $form->get('nompatient')->getData();
            $adrpatient = $form->get('adr')->getData();

            $datechaine = $date->format('Y-m-d H:i:s');

            
         
           

            //Server settings
            $mail->SMTPDebug = SMTP::DEBUG_SERVER;
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com';
            $mail->SMTPAuth   = true;
            $mail->Username   = 'amen.bejaoui@esprit.tn';             // SMTP username
            $mail->Password   = '22202227';                               // SMTP password
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port       = 587;


            // email pour dire a le docteur que une reservation a ete annuler (mouchar)

            //Recipients
            $mail->addAddress('amen.bejaoui@esprit.tn');     // Add a recipient
            // Content
            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = " Alert Annulation d'un Rendez_vous  ";
            $mail->Body    = "bonjour docteur, le Rendez-vous de Monsieur/Madame   ".$nom." ".$datechaine." a été annuler a ".$time;

            $mail->send();
            $this->addFlash('message','the email has been sent');

            //maint mail pour patient pour le dire que leur reservation et annuler

            $mail->addAddress( $adrpatient);     // Add a recipient
            // Content
            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = " Clinique Santiana   ";
            $mail->Body    = "bonjour Monsieur/Madame, votre Rendez-vous a été annuler pour plus 
            d'information veuillez nous contacter sur le numéro +216 72 258 741 ";

            $mail->send();
            $this->addFlash('message','the email has been sent');

        } catch (Exception $e) {
            echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        }
       
      
        $em->remove($rdv);
        $notifier->send(new Notification('Thank you for the ', ['browser']));
        $em->flush();
       
   

        return $this->redirectToRoute('affrdv');
    }


    
/**
     * @Route("/modifrdv/{idrdv}", name="modifrdv")
     */
    public function modifrdv(Request $request,$idrdv): Response
    {
        $rdv= $this->getDoctrine()->getRepository(Rdv::class)->find($idrdv);

        $form=$this->createForm(Rdv1Type::class,$rdv);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('app_rdv');
        }
        return $this->render('rdv/modifierrdv.html.twig',['f'=>$form->createView()]);
    }

    
 /**
     * @Route("rdv/recherchee",name="recherchee")
     */
    function Recherchee (RdvRepository $rep,Request $request)
    {
        $data =$request->get('search');

        $rdv=$rep->findBy(['idrdv'=>$data]);

        return $this->render('rdv/rdv.html.twig', [
            'r'=>$rdv]);



    }

     /**
     * @Route("/statistiques",name="statistiques")
     */
    public function statistiques(RdvRepository $repository)
    {


      
        return $this->render('rdv/statistique.html.twig');

    }

}


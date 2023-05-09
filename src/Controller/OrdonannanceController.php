<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use  Symfony\Component\Notifier\NotifierInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;


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



use Symfony\Component\Validator\Constraints\Json;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Exception\NotEncodableValueException;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;

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









use App\Entity\Ordonance;
use App\Form\OrdonanceType;
use App\Repository\OrdonanceRepository;



use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;









class OrdonannanceController extends AbstractController
{
    #[Route('/ordonnance', name: 'display_ordonance')]
    public function index(): Response
    {
        $ordonance= $this->getDoctrine()->getManager()->getRepository(Ordonance::class)->findAll();
       
        return $this->render('ordonnance/index.html.twig', [
            'c'=>$ordonance
         ]);
    }
    #[Route('/frontordonnance', name: 'display_ordonance_front')]
    public function index1(): Response
    {
        $ordonance= $this->getDoctrine()->getManager()->getRepository(Ordonance::class)->findAll();
       
        return $this->render('ordonnance/indexfront.html.twig', [
            'cc'=>$ordonance
         ]);
    }

    #[Route('/addordonnance', name: 'addordonnance')]
    public function addconsultation(Request $request): Response
    {
        $ordonance=new Ordonance(); 
        $form=$this->createForm(OrdonanceType::class,$ordonance);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
           
           
            $em=$this->getDoctrine()->getManager();
            $em->persist($ordonance);
            $em->flush();

            return $this->redirectToRoute('display_ordonance');
        }
        return $this->render('ordonnance/createordonnance.html.twig',['f'=>$form->createView()]);

       
    }
    
    #[Route('/removeOrdonnance/{id}', name: 'supp_ordonnance')]
    public function suppressionConsultation(Request $request ,Ordonance $ordonance,NotifierInterface $notifier): Response
    {
        $em=$this->getDoctrine()->getManager();
        $form=$this->createForm(OrdonanceType::class,$ordonance);
        $form->handleRequest($request);

        $em->remove($ordonance);
        $em->flush();

        $mail = new PHPMailer(true);
           

        try {
            
            $date = $form->get('date')->getData();
            $description = $form->get('description')->getData();
            $datechaine= $date->format('Y-m-d H:i:s');
            

            
         
           

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
            $mail->addAddress('mohamedabdelatif.aouadh@esprit.tn');     // Add a recipient
            // Content
            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = " Alert suppression d une ordonance  ";
            $mail->Body    = "bonjour docteur, l'ordonance a été supprimer de date  ".$datechaine."et la decription suivant :"."  ".$description;

            $mail->send();
            $this->addFlash('message','the email has been sent');

            

        } catch (Exception $e) {
            echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        }
       
       










      

        return $this->redirectToRoute('display_ordonance');

    }

    #[Route('/modifOrdonnance{id}', name: 'modifOrdonnance')]
    public function modifActivite(Request $request,$id): Response
    {   
        $ordonance= $this->getDoctrine()->getRepository(Ordonance::class)->find($id);

        $form=$this->createForm(OrdonanceType::class,$ordonance);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('display_ordonance');
        }
        return $this->render('ordonnance/updateOrdonnance.html.twig',['f'=>$form->createView()]);
    }

    /**
     * @Route("/listpdfm", name="listpdfm")
     */
    public function listpdfm(Request $request)
    {

        $entityManager = $this->getDoctrine();
        $list = $entityManager->getRepository(Ordonance::class)
            ->findAll();
        
        
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($pdfOptions);
        $html = $this->renderView('ordonnance/listpdf.html.twig', [
            'listpdf'=>$list,
        ]);
        $dompdf->loadHtml($html.'');
       
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();
      
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);


    }



    #[Route('/afficherord', name: 'afficher_ord')]
    public function indexAdminn(NormalizerInterface $normalizer)
    {
        $activites= $this->getDoctrine()->getManager()->getRepository(Ordonance::class)->findAll();
        $jsonContent=$normalizer->normalize($activites,'json',['groups'=>'Ordonance']);
           
        return new Response(json_encode($jsonContent)); 
    
    }


    /**
     * @Route("/ajouterord", name="ajouteroord")
     * @Method("POST")
     */

     public function ajouterord(Request $request,NormalizerInterface $normalizer)
     {
         $activite = new Ordonance() ;
        
         $date =  new \DateTime('now');
         $image=$request->query->get("image");
         $description=$request->query->get("description");
         $em = $this->getDoctrine()->getManager();
         $activite->setDate($date);
         $activite->setImage($image);
         $activite->setDescription($description);
         $em->persist($activite);
         $em->flush();
         $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Ordonance']);
            
         return new Response(json_encode($jsonContent)); 
 
     }
 
 
     /**
      * @Route("/deleteord/{id}", name="delete_ord")
      */
 
      public function deleteReclamationAction(Request $request,NormalizerInterface $normalizer) {
         $id = $request->get("id");
 
         $em = $this->getDoctrine()->getManager();
         $activite = $em->getRepository(Ordonance::class)->find($id);
         if($activite!=null ) {
             $em->remove($activite);
             $em->flush();
 
         }
         $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Ordonance']);
            
         return new Response(json_encode($jsonContent)); 
 
     }

     /**
     * @Route("/updateord/{id}", name="update_ord")
     */
    public function modifierOrdonnance(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $activite = $this->getDoctrine()->getManager()
            ->getRepository(Ordonance::class)
            ->find($request->get("id"));



            
            $activite->setImage($request->get("image"));
            $activite->setDescription($request->get("description"));

        $em->persist($activite);
        $em->flush();
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Ordonance']);
           
        return new Response(json_encode($jsonContent)); 
        

    }



     /**
     * @Route("rdv/recherchee",name="recherchee")
     */
    function Recherchee (OrdonanceRepository $rep,Request $request)
    {
        $data =$request->get('search');

        $rdv=$rep->findBy(['id'=>$data]);

        return $this->render('base.html.twig', [
            'r'=>$rdv]);



    }

    #[Route('/share/facebook/{id}', name: 'ssdd')]
    public function share($id, Request $request, OrdonanceRepository $repo): Response
    {
        $publication = $repo->find($id);

        $hashtag = "#" . str_replace(' ', '', $publication->getDescription());
        $homepageUrl = "http://127.0.0.1:8000/";
        $shareUrl = "https://www.facebook.com/dialog/share?app_id=160291406462337&display=popup&href=" . urlencode($homepageUrl);
        $shareUrl .= "&hashtag=" . urlencode($hashtag);

        return $this->redirect($shareUrl);
    }



}

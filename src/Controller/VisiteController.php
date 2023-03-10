<?php

namespace App\Controller;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Visite;
use App\Form\VisiteType;
use App\Repository\VisiteRepository;
use Symfony\Component\HttpFoundation\Request;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Dompdf\Options;
use Dompdf\Dompdf;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Form\FormView;


use Symfony\Component\String\Slugger\SluggerInterface;


use Symfony\Component\Serializer\SerializerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;




use Symfony\Component\Serializer\Normalizer\NormalizerInterface;





class VisiteController extends AbstractController
{
    


 /**
     * @Route("/affvisite", name="affvisite")
     */
    public function affvisite(): Response
    {
        $visite= $this->getDoctrine()->getManager()->getRepository(Visite::class)->findAll();
        return $this->render('visite/visite.html.twig', [
           'r'=>$visite
        ]);
       
    }


    /**
     * @Route("/suppvisite/{idvisite}", name="suppvisite")
     */
    public function suppvisite(Visite $visite/*,Swift_Mailer $mailer*/ ): Response
    {
        $em=$this->getDoctrine()->getManager();
        $em->remove($visite);
        $em->flush();
        /*$email = (new Swift_Message('Nouveau Article'))
            ->from('no-reply@Surterrain.com')
            ->to('amen.bejaoui@esprit.tn')
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject('Time for Symfony Mailer!')
            ->text('Sending emails is fun again!')
            ->html('<p>See Twig integration for better HTML integration!</p>');

        $mailer->send($email);*/

        return $this->redirectToRoute('affvisite');
    }

    

    /**
     * @Route("/modifvisite/{idvisite}", name="modifvisite")
     */
    public function modifvisite(Request $request,$idvisite): Response
    {
        $visite= $this->getDoctrine()->getRepository(Visite::class)->find($idvisite);

        $form=$this->createForm(VisiteType::class,$visite);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('affvisite');
        }
        return $this->render('visite/modifiervisite.html.twig',['f'=>$form->createView()]);
    }

 /**
     * @Route("/listvisitepdf", name="listpdf")
     */
    public function listpdf(Request $request)
    {

        $entityManager = $this->getDoctrine();
        $list = $entityManager->getRepository(Visite::class)
            ->findAll();


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        //$pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $html=$this->render('visite/listpdf.html.twig', [
            'listpdf' => $list
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html.'l');

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("visite/listpdf.html.twig", [
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
     * @Route("/ajoutervisite", name="ajoutervisite")
     */
    public function ajoutervisite(Request $request, SluggerInterface $slugger): Response
    {
       $visite=new Visite();
       $form=$this->createForm(VisiteType::class,$visite);
       $form->handleRequest($request);
       if ($form->isSubmitted() && $form->isValid()) {

// partie image up




  


////////////////
        $em=$this->getDoctrine()->getManager();
        $em->persist($visite);//add
        $em->flush();
        
     
        return $this->render('visite/confirmation.html.twig');
    }
    return $this->render('visite/createvisite.html.twig',['f'=>$form->createView()]);
}



#[Route('/searchStudentx', name: 'searchStudentx')]


public function searchStudentx(Request $request,NormalizerInterface $Normalizer,VisiteRepository $sr)
{
    $repository = $this->getDoctrine()->getRepository(Visite::class);
    $requestString=$request->get('searchValue');
    $students = $sr->findStudentByNsc($requestString);
    $jsonContent = $Normalizer->normalize($students, 'json',['groups'=>'visite']);
    $retour=json_encode($jsonContent);
    return new Response($retour);
  



}



     /**
     * @Route("/affichervj", name="affichervj")
     */
    public function affichervj(NormalizerInterface $normalizer)
    {
        $activites= $this->getDoctrine()->getManager()->getRepository(Visite::class)->findAll();
        $jsonContent=$normalizer->normalize($activites,'json',['groups'=>'visite']);
           
        return new Response(json_encode($jsonContent)); 
    
    }


     /**
     * @Route("/ajoutervisitej", name="ajoutervisitej")
     * @Method("POST")
     */

    public function ajoutervisitej(Request $request,NormalizerInterface $normalizer)
    {
        $activite = new Rdv() ;
       
       $date =  new \DateTime('now');
       $nomvisiteur = $request->query->get("nomvisiteur");
        $nompatient = $request->query->get("nompatient");
        $numerotel= $request->query->get("numerotel");
        $message= $request->query->get("message");
     
       
        
     
        $em = $this->getDoctrine()->getManager();


        $activite->setDate($date);
        $activite->setNompatient($nompatient);
        $activite->setNumerotel($numerotel);
        $activite->setMessage($message);
        $activite->setAdr($nomvisiteur);
      
      


        $em->persist($activite);
        $em->flush();
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'visite']);
           
        return new Response(json_encode($jsonContent)); 

    }

      /**
     * @Route("/suppvisitej/{idvisite}", name="suppvisitej")
     */

    public function suppvisitej(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("idvisite");

        $em = $this->getDoctrine()->getManager();
        $activite = $em->getRepository(Visite::class)->find($id);
        if($activite!=null ) {
            $em->remove($activite);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'visite']);
           
        return new Response(json_encode($jsonContent)); 

    }


     /**
     * @Route("/modifvisitej/{idrdv}", name="modifvisitej")
     */
    public function modifvisitej(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $activite = $this->getDoctrine()->getManager()
            ->getRepository(Visite::class)
            ->find($request->get("idvisite"));



            $activite->setNomvisiteur($request->get("nomvisiteur"));
            $activite->setNompatient($request->get("nompatient"));
            $activite->setNumerotele($request->get("numerotele"));
            $activite->setMesage($request->get("message"));
        $date =  new \DateTime('now');



        $em->persist($activite);
        $em->flush();
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'visite']);
           
        return new Response(json_encode($jsonContent)); 
        

    }
}

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

        $em->remove($ordonance);
        $em->flush();

        $mail = new PHPMailer(true);
           

        try {
            
           

            
         
           

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
            $mail->Body    = "bonjour docteur, l'ordonance a été supprimer   ";

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


}

<?php

namespace App\Controller;

use App\Entity\Service;
use App\Form\ServiceType;
use App\Repository\ServiceRepository;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;




class ServiceController extends AbstractController
{
    #[Route('/', name: 'display_service')]
    public function index(): Response
    {
        $services = $this->getDoctrine()->getRepository(Service::class)->findAll();
        return $this->render('service/index.html.twig', [
            's'=>$services
        ]);
    }

    #[Route('/frontservice', name: 'display_service_front')]
    public function index1(Request $request, PaginatorInterface $paginator): Response
    {
        $services = $this->getDoctrine()->getRepository(Service::class)->findAll();

        $services = $paginator->paginate(
            $services, /* query NOT result */
            $request->query->getInt('page', 1),
            2
        );
        return $this->render('service/indexfront.html.twig', [
            'cc'=>$services
        ]);
    }

    #[Route('/listpdf', name: 'listpdf')]
    public function genpdf( Request $request)
    {
        $entityManager = $this->getDoctrine();
        $list = $entityManager->getRepository(Service::class)
            ->findAll();
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($pdfOptions);
        $html = $this->renderView('service/pdf.html.twig', [
            'listpdf'=>$list,
        ]);
        $dompdf->loadHtml($html);
        $dompdf->setPaper('A4', 'portrait');
        $dompdf->render();
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => true
        ]);


    }



    #[Route('/addService', name: 'addService')]
    public function addService(Request $request): Response
    {
        $service = new Service();
        $form = $this->createForm(ServiceType::class,$service);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){

            $en = $this->getDoctrine()->getManager();
            /////////////////////////////////////////////////////
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
                $mail->addAddress('yosr.teffaha@esprit.tn');     // Add a recipient
                // Content
                $mail->isHTML(true);                                  // Set email format to HTML
                $mail->Subject = " service ajouter  ";
                $mail->Body    = "bonjour docteur, le service a été ajouter!!   ";

                $mail->send();



            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }













            ////////////////////////////////////////
            $en->persist($service);
            $en->flush();$this->get('session')->getFlashBag()->add('notice','Validation Faite Avec Succés');
            return $this->redirectToRoute('display_service');

        }
        return $this->render('service/createService.html.twig',['f'=>$form->createView()]);

    }

    #[Route('/removeService/{id}', name: 'supp_service')]
    public function suppresionService( Service  $service): Response
    {
        $en = $this->getDoctrine()->getManager();
        $en->remove($service);
        $en->flush();
        return $this->redirectToRoute('display_service');


    }
    #[Route('/modifService/{id}', name: 'modifService')]
    public function modifService(Request $request,$id): Response
    {
        $service = $this->getDoctrine()->getManager()->getRepository(Service::class)->find($id);
        $form = $this->createForm(ServiceType::class,$service);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $en = $this->getDoctrine()->getManager();
            $en->flush();
            return $this->redirectToRoute('display_service');

        }
        return $this->render('service/updateService.html.twig',['f'=>$form->createView()]);

    }

}

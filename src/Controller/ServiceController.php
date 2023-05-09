<?php

namespace App\Controller;

use App\Entity\Service;
use App\Form\ServiceType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\Validator\Constraints\Json;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\SerializerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Serializer\Serializer;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
use App\Repository\ServiceRepository;

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



    #[Route('/admin', name: 'display_admin')]
    public function indexAdmin(): Response
    {

        return $this->render('Admin/index.html.twig'

        );
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

                $nom = $form->get('nom')->getData();





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
                $mail->Body    = "bonjour docteur, le service a été ajouter!!   ".$nom;

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
    #[Route('/listpdfy', name: 'listpdfy')]
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
    /**
     * @Route("/addServ", name="add_act")
     * @Method("POST")
     */

    public function ajouterServiceAction(Request $request,NormalizerInterface $normalizer)
    {
        $service = new Service() ;
        $nom = $request->query->get("nom");

        $description= $request->query->get("description");
        $image= $request->query->get("image");
        $em = $this->getDoctrine()->getManager();


        $service->setNom($nom);
        $service->setDescription($description);
        $service->setImage($image);


        $em->persist($service);
        $em->flush();
        $jsonContent=$normalizer->normalize($service,'json',['groups'=>'Service']);

        return new Response(json_encode($jsonContent));

    }
    /**
     * @Route("/deleteserv/{id}", name="delete_serv")
     */

    public function deleteServiceAction(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $service = $em->getRepository(Service::class)->find($id);
        if($service!=null ) {
            $em->remove($service);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($service,'json',['groups'=>'Service']);

        return new Response(json_encode($jsonContent));

    }

    /**
     * @Route("/updateserv/{id}", name="update_serv")
     */
    public function modifierServiceAction(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $service = $this->getDoctrine()->getManager()
            ->getRepository(Service::class)
            ->find($request->get("id"));



        $service->setNom($request->get("nom"));
        $service->setDescription($request->get("description"));
        $service->setImage($request->get("image"));

        $em->persist($service);
        $em->flush();
        $jsonContent=$normalizer->normalize($service,'json',['groups'=>'Service']);

        return new Response(json_encode($jsonContent));


    }
    /**
     * @Route("/Displayjson", name="display_admin1")
     */
    public function indexAdminn(NormalizerInterface $normalizer)
    {
        $service= $this->getDoctrine()->getManager()->getRepository(Service::class)->findAll();
        $jsonContent=$normalizer->normalize($service,'json',['groups'=>'Service']);

        return new Response(json_encode($jsonContent));

    }

    #[Route('/share/facebook/{id}', name: 'sssdd')]
    public function share($id, Request $request, ServiceRepository $repo): Response
    {
        $publication = $repo->find($id);

        $hashtag = "#" . str_replace(' ', '', $publication->getNom());
        $homepageUrl = "http://127.0.0.1:8000/";
        $shareUrl = "https://www.facebook.com/dialog/share?app_id=160291406462337&display=popup&href=" . urlencode($homepageUrl);
        $shareUrl .= "&hashtag=" . urlencode($hashtag);

        return $this->redirect($shareUrl);
    }
    #[Route('/Service/{id}/like', name: 'Question_like')]
    #[Route('/Service/{id}/dislike', name: 'Question_dislike')]
    public function likeOrDislike(Service $Question, Request $request): Response
    {
        if ($request->get('_route') === 'Question_like') {
            $Question->setLikes($Question->getLikes() + 1);
        } else {
            $Question->setDislikes($Question->getDislikes() + 1);
        }

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($Question);
        $entityManager->flush();

        return $this->redirectToRoute('display_service_front', ['id' => $Question->getId()]);
    }

}

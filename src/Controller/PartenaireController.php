<?php

namespace App\Controller;
use App\Controller\barChart;
use App\Entity\Partenaire;
use App\Form\Partenaire3Type;
use App\Repository\PartenaireRepository;

use Endroid\QrCode\Builder\Builder;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelHigh;
use Endroid\QrCode\Label\Alignment\LabelAlignmentCenter;
use Endroid\QrCode\Label\Font\NotoSans;
use Endroid\QrCode\QrCode;
use Endroid\QrCode\RoundBlockSizeMode\RoundBlockSizeModeMargin;
use Endroid\QrCode\Writer\PngWriter;
use Endroid\QrCodeBundle\Response\QrCodeResponse;
use Knp\Component\Pager\PaginatorInterface;
use MercurySeries\FlashyBundle\FlashyNotifier;
use Normalizer;
use Endroid\QrCode\Writer\SvgWriter;
use PHPMailer\PHPMailer\Exception;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use symfony\component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\Mailer;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mime\Message;
use Symfony\Component\Notifier\Notification\Notification;
use Symfony\Component\Notifier\NotifierInterface;
use Symfony\Component\Routing\Annotation\Route;
use symfony\component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;


#[Route('/partenaire')]
/**
 * Summary of PartenaireController
 */
class PartenaireController extends AbstractController
{
    #[Route('/', name: 'app_partenaire_index', methods: ['GET'])]
    public function index(Request $request, PartenaireRepository $partenaireRepository): Response
    {
        $query = $request->query->get('q');
        if ($query) {
            $partenaires = $partenaireRepository->searchPartenaireByString($query);
        } else {
            $partenaires = $partenaireRepository->findAll();
        }
    
        return $this->render('partenaire/index.html.twig', [
            'partenaires' => $partenaires,
            'query' => $query,
        ]);
    }
    #[Route('/new', name: 'app_partenaire_new')]
    public function addconsultation(Request $request): Response
    {
        $certificat=new Partenaire(); 
        $form=$this->createForm(Partenaire3Type::class,$certificat);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
           
           
            $em=$this->getDoctrine()->getManager();
            $em->persist($certificat);
            $em->flush();

            $mail = new PHPMailer(true);
           

            try {
                
                
                
    
                
             
               
    
                //Server settings
                $mail->SMTPDebug = SMTP::DEBUG_SERVER;
                $mail->isSMTP();
                $mail->Host       = 'smtp.gmail.com';
                $mail->SMTPAuth   = true;
                 
                $mail->Username   = 'emna.baccar@esprit.tn';            
                $mail->Password   = '223JFT3808';                         // SMTP password
                $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
                $mail->Port       = 587;
    
    
                // email pour dire a le docteur que une reservation a ete annuler (mouchar)
    
                //Recipients
                $mail->addAddress('emna.baccar@esprit.tn');     // Add a recipient
                // Content
                $mail->isHTML(true);                                  // Set email format to HTML
                $mail->Subject = " Alert Ajouter un partenaire  ";
                $mail->Body    = "Partenaire a ajouter avec succes :)  ";
    
                $mail->send();
                $this->addFlash('message','the email has been sent');
    
                
    
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }
           
           
    
    





            return $this->redirectToRoute('app_partenaire_index');
        }
        return $this->renderForm('partenaire/new.html.twig', [
            'partenaire' => $certificat,
            'form' => $form,
        ]);

       
    }
    
   /* #[Route('/new', name: 'app_partenaire_new', methods: ['GET', 'POST'])]
    
    public function new(Request $request, PartenaireRepository $partenaireRepository,  FlashyNotifier $flashy, Partenaire $partenaire , $result): Response
    {
        $partenaire = new Partenaire();
        $form = $this->createForm(Partenaire3Type::class, $partenaire);
        $form->handleRequest($request);
        
        $mail = new PHPMailer(true);
        if ($form->isSubmitted() && $form->isValid()) {
            $partenaireRepository->save($partenaire, true);
       try {
            
            $time = date('H:i:s \O\n d/m/Y');
           
            $nom = $form->get('nom')->getData();
            $email = $form->get('email')->getData();

           
            $mail->SMTPDebug = SMTP::DEBUG_SERVER;
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com';
            $mail->SMTPAuth   = true;
            $mail->Username   = 'emna.baccar@esprit.tn';            
            $mail->Password   = '223JFT3808';                               
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port       = 587;
            $mail->addAddress( $email);     
            $mail->isHTML(true);                                  
            $mail->Subject = " Clinique Santiana   ";
            $mail->Body    = "Bravo!! le partenaire a été ajouté avec succes ";

            $mail->send();
            $this->addFlash('message','the email has been sent');
            $flashy->success('Article Ajoutée','http://your-awesome-link.com');

        } catch (Exception $e) {
            echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        }  
        $this->addFlash('success', 'Entity created successfully!');
        /*$notifier->send(new Notification('Thank you for the ', ['browser']));
          return $this->redirectToRoute('app_partenaire_index', [], Response::HTTP_SEE_OTHER);
        }
        return $this->renderForm('partenaire/new.html.twig', [
            'partenaire' => $partenaire,
            'form' => $form,
        ]);
    }
   
   
*/
    #[Route('/{id}', name: 'app_partenaire_show', methods: ['GET'])]
    public function show(Partenaire $partenaire): Response
    {
        return $this->render('partenaire/show.html.twig', [
            'partenaire' => $partenaire,
        ]);
    }
    #[Route('/notfound', name : 'not_found')]
    public function notFound(): Response
    {
        return $this->render('error_pages/403.html.twig');
    }

    #[Route('/{id}/edit', name: 'app_partenaire_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Partenaire $partenaire, PartenaireRepository $partenaireRepository): Response
    {
        $form = $this->createForm(Partenaire3Type::class, $partenaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $partenaireRepository->save($partenaire, true);
            //$flashy->warning('Client Modifié', 'http://your-awesome-link.com');

            return $this->redirectToRoute('app_partenaire_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('partenaire/edit.html.twig', [
            'partenaire' => $partenaire,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_partenaire_delete', methods: ['POST'])]
    /**
     * Summary of delete
     * @param Request $request
     * @param Partenaire $partenaire
     * @param PartenaireRepository $partenaireRepository
     * @param FlashyNotifier $flashy
     * @return Response
     */
    public function delete(Request $request, Partenaire $partenaire, PartenaireRepository $partenaireRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $partenaire->getId(), $request->request->get('_token'))) {
            $partenaireRepository->remove($partenaire, true);
          //  $flashy->error('Admin Supprimé', 'http://your-awesome-link.com');
        }

        $mail = new PHPMailer(true);
           

        try {
            
            
            

            
         
           

            //Server settings
            $mail->SMTPDebug = SMTP::DEBUG_SERVER;
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com';
            $mail->SMTPAuth   = true;
             
            $mail->Username   = 'emna.baccar@esprit.tn';            
            $mail->Password   = '223JFT3808';                         // SMTP password
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port       = 587;


            // email pour dire a le docteur que une reservation a ete annuler (mouchar)

            //Recipients
            $mail->addAddress('emna.baccar@esprit.tn');     // Add a recipient
            // Content
            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = " Alert suppression d un partenaire  ";
            $mail->Body    = "un partenaire a ete suppimer !!!!!!!  ";

            $mail->send();
            $this->addFlash('message','the email has been sent');

            

        } catch (Exception $e) {
            echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        }
       
       




        return $this->redirectToRoute('app_partenaire_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/ajouterPar', name: 'ajouterPar')]

    public function ajouterPar(Request $request, NormalizerInterface $normalizer, PartenaireRepository $rep)
    {
        $partenaire = new Partenaire();
        $nom = $request->query->get("nom");
        $email = $request->query->get("email");





        $partenaire->setNom($nom);
        $partenaire->setEmail($email);
        $rep->save($partenaire, true);
        $jsonContent = $normalizer->normalize($partenaire, 'json', ['groups' => 'partenaire']);

        return new Response(json_encode($jsonContent));
    }




    #[Route('/updatepartenaire/{id}', name: 'update_partenaire')]

    public function modifierPar(Request $request, NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $partenaire = $this->getDoctrine()->getManager()
            ->getRepository(partenaire::class)
            ->find($request->get($id));



        $partenaire->setNom($request->get("nom"));
        $partenaire->setEmail($request->get("email"));

        $em->persist($partenaire);
        $em->flush();
        $jsonContent = $normalizer->normalize($partenaire, 'json', ['groups' => 'partenaire']);

        return new Response(json_encode($jsonContent));
    }

    #[Route('/deleteAct/{id}', name: 'delete_act')]
    public function deletePar(Request $request, NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $partenaire = $em->getRepository(Partenaire::class)->find($id);
        if ($partenaire != null) {
            $em->remove($partenaire);
            $em->flush();
        }
        $jsonContent = $normalizer->normalize($partenaire, 'json', ['groups' => 'partenaire']);

        return new Response(json_encode($jsonContent));
    }
    #[Route('/displayPar', name: 'displayPar')]
    public function indexAdminn(NormalizerInterface $normalizer)
    {
        $partenaires = $this->getDoctrine()->getManager()->getRepository(Partenaire::class)->findAll();
        $jsonContent = $normalizer->normalize($partenaires, 'json', ['groups' => 'partenaire']);

        return new Response(json_encode($jsonContent));
    }
}

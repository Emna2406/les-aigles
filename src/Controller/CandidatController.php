<?php

namespace App\Controller;


use App\Form\Candidat1Type;
use App\Repository\CandidatRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Normalizer;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use App\Entity\Candidat;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface; 
use MercurySeries\FlashyBundle\FlashyNotifier;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

#[Route('/candidat')]
class CandidatController extends AbstractController
{
    #[Route('/', name: 'app_candidat_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $candidats = $entityManager
            ->getRepository(Candidat::class)
            ->findAll();

        return $this->render('candidat/index.html.twig', [
            'candidats' => $candidats,
        ]);
    }
   




    #[Route('/new', name: 'app_candidat_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $candidat = new Candidat();
        $form = $this->createForm(Candidat1Type::class, $candidat);
        $form->handleRequest($request);
        $mail = new PHPMailer(true);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($candidat);
            $entityManager->flush();

            try {
            
                $time = date('H:i:s \O\n d/m/Y');
               
                $nom = $form->get('nom')->getData();
                $email = $form->get('email')->getData();
    
               
                $mail->SMTPDebug = SMTP::DEBUG_SERVER;
                $mail->isSMTP();
                $mail->Host       = 'smtp.gmail.com';
                $mail->SMTPAuth   = true;
                $mail->Username   = 'nouha.ouertani@esprit.tn';            
                $mail->Password   = 'Nounou2901';                               
                $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
                $mail->Port       = 587;

               
    
                $mail->addAddress( $email);     
                $mail->isHTML(true);                                  
                $mail->Subject = " Clinique Santiana   ";
                $mail->Body    = "Bravo!! le candidat a été ajouté avec succes ";
    
                $mail->send();
                $this->addFlash('message','the email has been sent');
                
    
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }

            return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('candidat/new.html.twig', [
            'candidat' => $candidat,
            'form' => $form,
        ]);
    }

    #[Route('/{idCand}', name: 'app_candidat_show', methods: ['GET'])]
    /*
* @ParamConverter("id", class="Candidat", options={"id": "id"})
*/
    public function show(Candidat $candidat): Response
    {
        return $this->render('candidat/show.html.twig', [
            'candidat' => $candidat,
        ]);
    }

    #[Route('/{idCand}/edit', name: 'app_candidat_edit', methods: ['GET', 'POST'])]
    
    public function edit(Request $request, Candidat $candidat, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(Candidat1Type::class, $candidat);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('candidat/edit.html.twig', [
            'candidat' => $candidat,
            'form' => $form,
        ]);
    }

    #[Route('/{idCand}', name: 'app_candidat_delete', methods: ['POST'])]
    /*
* @ParamConverter("id", class="Candidat", options={"id": "id"})
*/
    public function delete(Request $request, Candidat $candidat, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$candidat->getIdCand(), $request->request->get('_token'))) {
            $entityManager->remove($candidat);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_candidat_index', [], Response::HTTP_SEE_OTHER);
    }


    #[Route("/AllCandidats", name: "list")]
    //* Dans cette fonction, nous utilisons les services NormlizeInterface et StudentRepository, 
    //* avec la méthode d'injection de dépendances.
    public function getCandidats(CandidatRepository $repo, SerializerInterface $serializer)
    {
        $candidats = $repo->findAll();
        //* Nous utilisons la fonction normalize qui transforme le tableau d'objets 
        //* students en  tableau associatif simple.
        // $studentsNormalises = $normalizer->normalize($students, 'json', ['groups' => "students"]);

        // //* Nous utilisons la fonction json_encode pour transformer un tableau associatif en format JSON
        // $json = json_encode($studentsNormalises);

        $json = $serializer->serialize($candidats, 'json', ['groups' => "candidats"]);

        //* Nous renvoyons une réponse Http qui prend en paramètre un tableau en format JSON
        return new Response($json);
    }





    #[Route('/Candidat/{id}', name: 'candidat')]
    /*
* @ParamConverter("id", class="Candidat", options={"id": "id"})
*/
    public function CandidatId($idCand, NormalizerInterface $normalizer, CandidatRepository $repo)
    {
        $candidat = $repo->find($idCand);
        $candidatNormalises = $normalizer->normalize($candidat, 'json', ['groups' => "Candidats"]);
        return new Response(json_encode($candidatNormalises));
    }
    
    
    #[Route('updateCandidatJSON/{id}', name: 'updateCandidatJSON')]
    /*
* @ParamConverter("id", class="Candidat", options={"id": "id"})
*/

    public function updateCandidatJSON(Request $req, $idCand, NormalizerInterface $Normalizer)
  {
    $em = $this->getDoctrine()->getManager();
    $candidat = $em->getRepository(Candidat::class)->find($idCand);
    $candidat->setNom($req->get('nom'));
    $candidat->setEmail($req->get('email'));
    $candidat->setPrenom($req->get('Prenom'));
    $candidat->setCV($req->get('CV'));
    $em->flush();

    $jsonContent = $Normalizer->normalize($candidat, 'json', [
        'groups' => 'Candidats'
    ]);

    return new JsonResponse($jsonContent);
  }


    /*#[Route('/deleteCandidatJSON/{id}', name: 'deleteCandidatJSON')]
    public function deleteCandidatJSON(Request $req, $id, NormalizerInterface $Normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $candidat = $em->getRepository(Candidat::class)->find($id);
        $em->remove($candidat);
        $em->flush();


        $jsonContent = $Normalizer->normalize($candidat, 'json', ['groups' => "candidats"]);
        return new Response("candidats deleted successfully" . json_encode($jsonContent));
    }*/


    #[Route('/affichercand', name: 'afficher_candidat')]
    public function indexAdminn(NormalizerInterface $normalizer, Request $request, CandidatRepository $candRepository): Response
    {
        $activities = $candRepository->findAll();
        $jsonContent=$normalizer->normalize($activities,'json',['groups'=>'Candidats']);
        
        return new JsonResponse($jsonContent); 
    }



    #[Route('/deletecand/{id}', name : 'delete_candid')]
    /*
* @ParamConverter("id", class="Candidat", options={"id": "id"})
*/

    public function deleteCandAction(Request $request,NormalizerInterface $normalizer) {
        $idCand = $request->get("idCand");

        $em = $this->getDoctrine()->getManager();
        $activite = $em->getRepository(Candidat::class)->find($idCand);
        if($activite!=null ) {
            $em->remove($activite);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Candidats']);
           
        return new Response(json_encode($jsonContent)); 

    }


}

<?php

namespace App\Controller;

use App\Entity\Candidat;
use App\Repository\CandidatRepository;
use Doctrine\ORM\EntityManagerInterface;
use PHPMailer\PHPMailer\Exception;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use App\Form\Candidat1Type;


#[Route('/candidatb')]
class CandidatbController extends AbstractController
{
    #[Route('/', name: 'app_candidatb_index1', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $candidats = $entityManager
            ->getRepository(Candidat::class)
            ->findAll();

        return $this->render('candidatb/index1.html.twig', [
            'candidats' => $candidats,
        ]);
    }
    #[Route('/', name: 'app_candidatb_form1', methods: ['GET'])]
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nom', TextType::class)
            ->add('prenom', TextType::class)
            
            ->add('submit', SubmitType::class, ['label' => 'Submit']);
    }
    
/**
     * @Route("/listpdf", name="listpdf")
     */
/*    public function listpdf(Request $request)
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


    } */
public function candidateForm(Request $request , EntityManagerInterface $entityManager)
{
    $form = $this->createForm(CandidateType::class);

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $data = $form->getData();

        // Persist the data to the database
        // ...

        // Redirect the user to a success page
        return $this->redirectToRoute('success');
    }
    if ($form->isSubmitted() && $form->isValid()) {
        $data = $form->getData();

        $candidat = new Candidat();
        $candidat->setNom($data['nom']);
        $candidat->setPrenom($data['prenom']);
        

        $entityManager->persist($candidat);
        $entityManager->flush();

        // Redirect the user to a success page
        return $this->redirectToRoute('success');
    }

    return $this->render('candidatb/form1.html.twig', [
        'form' => $form->createView(),
    ]);
}

#[Route('/new', name: 'app_candidatf_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager,CandidatRepository $candidatRepo): Response
    {
        $candidat = new Candidat();
        $form = $this->createForm(Candidat1Type::class, $candidat);
        $form->handleRequest($request);
        $mail = new PHPMailer(true);

        if ($form->isSubmitted() && $form->isValid()) {
            $candidatRepo->save($candidat,true);


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

               
    
                $mail->addAddress('nouha.ouertani@esprit.tn');     
                $mail->isHTML(true);                                  
                $mail->Subject = " Clinique Santiana   ";
                $mail->Body    = "Bravo!! le candidat a été ajouté avec succes ";
    
                $mail->send();
                // $entityManager->persist($candidat);
                // $entityManager->flush();
                $this->addFlash('message','the email has been sent');
                
    
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }

            return $this->redirectToRoute('app_candidatb_index1', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('candidat/candidatFront.html.twig', [
            'candidat' => $candidat,
            'form' => $form,
        ]);
    }





}

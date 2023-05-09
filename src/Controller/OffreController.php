<?php

namespace App\Controller;

use App\Entity\Offre;
use App\Form\Offre1Type;
use App\Repository\OffreRepository;
use Doctrine\ORM\EntityManagerInterface;
use App\Controller\FlashNotifier;
use Knp\Component\Pager\PaginatorInterface;
use Normalizer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bundle\MakerBundle\Util\ClassNameDetails;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\Flash\FlashBagInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\CircularReferenceHandlerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;







#[Route('/offre')]

/**
 * Summary of OffreController
 */
class OffreController extends AbstractController
{
    #[Route('/', name: 'app_offre_index', methods: ['GET'])]
public function index(Request $request,OffreRepository $rep, EntityManagerInterface $entityManager, PaginatorInterface $paginator): Response
{
    

 
    //////////// Recherche ////////////
    $query = $request->query->get('q');

    if ($query) {
        $offres = $entityManager
            ->getRepository(Offre::class)
            ->createQueryBuilder('o')
            ->where('o.title LIKE :query')
            ->setParameter('query', '%'.$query.'%')
            ->getQuery()
            ->getResult();
            $offres = $paginator->paginate($offres, $request->query->getInt('page', 1), 3);

    } else {
        $offres = $entityManager
            ->getRepository(Offre::class)
            ->findAll();
            $offres = $paginator->paginate($offres, $request->query->getInt('page', 1), 3);

    }
    
    return $this->render('offre/index.html.twig', [
        'offres' => $offres,
        
    ]);
}
/**
     * @Route("/stats", name="stats")
     */
    public function statisOffre(OffreRepository $OffreRepository)
{
      //on va chercher les categories
      $rech = $OffreRepository->barDep();
      $arr = $OffreRepository->barArr();
      
      $bar = new barChart ();
      $bar->getData()->setArrayToDataTable(
          [['Offre', 'Nombres des candidats'],
           ['1', intVal($rech)],
           ['2', intVal($arr)],
          
  
          ]
      );
        

        
  
      $bar->getOptions()->setTitle('Les Offres');
      $bar->getOptions()->getHAxis()->setTitle('Nombre de Offre');
      $bar->getOptions()->getHAxis()->setMinValue(0);
      $bar->getOptions()->getVAxis()->setTitle('Nombres des candidats postulés');
      $bar->getOptions()->SetWidth(800);
      $bar->getOptions()->SetHeight(400);


    
  
  
      return $this->render('offre/stats.html.twig', array('bar'=> $bar )); 
    }

#[Route('/list', name: 'app_listoffre_indexF', methods: ['GET'])]
public function indexF(Request $request,OffreRepository $rep, EntityManagerInterface $entityManager, PaginatorInterface $paginator): Response
{
    $query = $request->query->get('q');

    if ($query) {
        $offres = $entityManager
            ->getRepository(Offre::class)
            ->createQueryBuilder('o')
            ->where('o.description LIKE :query')
            ->setParameter('query', '%'.$query.'%')
            ->getQuery()
            ->getResult();
            $offres = $paginator->paginate($offres, $request->query->getInt('page', 1), 3);
    } else {
        $offres = $entityManager
            ->getRepository(Offre::class)
            ->findAll();
            $offres = $paginator->paginate($offres, $request->query->getInt('page', 1), 3);
    }
    
    return $this->render('offre/indexF.html.twig', [
        'offres' => $offres,
        
        'query' => $query,
    ]);
}
    #[Route('/new', name: 'app_offre_new', methods: ['GET', 'POST'])]
   
    public function new(Request $request, EntityManagerInterface $entityManager, OffreRepository $offreRepo): Response
    {
        $offre = new Offre();
        $form = $this->createForm(Offre1Type::class, $offre);
        $form->handleRequest($request);
        $mail = new PHPMailer(true);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($offre);
            $entityManager->flush();

            try {
            
                $time = date('H:i:s \O\n d/m/Y');
               
                $description = $form->get('description')->getData();
                $nbrplaces = $form->get('nbrPlaces')->getData();
                $idService = $form->get('idService')->getData();
               
                $mail->SMTPDebug = SMTP::DEBUG_SERVER;
                $mail->isSMTP();
                $mail->Host       = 'smtp.gmail.com';
                $mail->SMTPAuth   = true;
                $mail->Username   = 'nouha.ouertani@esprit.tn';            
                $mail->Password   = 'Nounou2901';                               
                $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
                $mail->Port       = 587;

               
    
               $mail->addAddress( 'nouha.ouertani@esprit.tn');    
                $mail->isHTML(true);                                  
                $mail->Subject = " Clinique Santiana   ";
                $mail->Body    = "Bravo!! le candidat a été ajouté avec succes ";
    
                $mail->send();
                $this->addFlash('message','the email has been sent');
                
                
    
            } catch (Exception $e) {
                echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
            }

            return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
        }


         

        return $this->renderForm('offre/new.html.twig', [
            'offre' => $offre,
            'form' => $form,
        ]);
    }

    #[Route('/show/{id}', name: 'app_offre_show')]
    public function show(Offre $offre): Response
    {
        return $this->render('offre/show.html.twig', [
            'offre' => $offre,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_offre_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, offre $offre, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(Offre1Type::class, $offre);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('offre/edit.html.twig', [
            'offre' => $offre,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_offre_delete', methods: ['POST'])]
    public function delete(Request $request, Offre $offre, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$offre->getid(), $request->request->get('_token'))) {
            $entityManager->remove($offre);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_offre_index', [], Response::HTTP_SEE_OTHER);
    }

    #[Route("/ajoutercert", name:"ajoutercertt")]
     public function ajouteroff(Request $request,NormalizerInterface $normalizer)
     {
         $activite = new Offre() ;
        
         
         $description=$request->query->get("description");
         $nbrPlaces=$request->query->get("nbrPlaces");
         $idservice=$request->query->get("IdService");
         $em = $this->getDoctrine()->getManager();
         $activite->setDescription($description);
         $activite->setNbrPlaces($nbrPlaces);
         $activite->setidService($nbrPlaces);
         
         $em->persist($activite);
         $em->flush();
         $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Offre']);
            
         return new Response(json_encode($jsonContent)); 
 
     }
   
    


     
 /*********************supprimer ****************************/


/**
     * @Route("/deleteoffre/{idOffre}", name="delete_off")
     */

     public function deleteOffreAction(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $offre = $em->getRepository(Offre::class)->find($id);
        if($offre!=null ) {
            $em->remove($offre);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($offre,'json',['groups'=>'Offre']);
           
        return new Response(json_encode($jsonContent)); 

    }
    #[Route('/affichercert', name: 'affichercert')]
    public function affichercert(NormalizerInterface $normalizer, OffreRepository $repos): Response
    {
        $offre= $repos->findAll();
        $offrenormalises = $normalizer->normalize($offre,'json',['groups'=>"Offre"]);
        $json = json_encode($offrenormalises); 
        return new Response($json); 
    }



  /***************************Modifier***************************/

        /**
        * @Route("/updateOffre", name="update_offre")
        * @Method("PUT")
        */

public function ModifierOffreAction (Request $request) {

    $em = $this->getDoctrine()->getManager();
    $offre = $this->getDoctrine()->getManager()
            ->getRepository(Offre::class)
            ->find($request->get("id"));
    $offre->setDescription($request->get("description"));
    $offre->setNbrPlaces($request->get("NbrPlaces"));
    
    
    $em->persist($offre);
    $em->flush();
    $serializer = new Serializer([new ObjectNormalizer()]);
    $formatted = $serializer->normalize($offre);
    return new JsonResponse("Offre a été bien modifiée.");
 }



    
    
 



}
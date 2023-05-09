<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Options;
use App\Entity\Consultation;
use App\Form\ConsultationType;
use App\Repository\OrdonanceRepository;
use App\Repository\ConsultationRepository;
use Dompdf\Dompdf;
use Symfony\Component\HttpFoundation\Request;

use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use symfony\component\HttpFoundation\JsonResponse;
use symfony\component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;




use Doctrine\ORM\EntityManagerInterface;

use Symfony\Component\Serializer\Serializer;

class ConsultationController extends AbstractController
{
    #[Route('/consultation', name: 'display_consultation')]
    public function index(): Response
    {
        $consultation= $this->getDoctrine()->getManager()->getRepository(Consultation::class)->findAll();
       
        return $this->render('consultation/index.html.twig', [
            'c'=>$consultation
         ]);
    }

   

   


    #[Route('/addconsultation', name: 'addconsultation')]
    public function addconsultation(Request $request): Response
    {
        $consultation=new Consultation(); 
        $form=$this->createForm(ConsultationType::class,$consultation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
           
           
            $em=$this->getDoctrine()->getManager();
            $em->persist($consultation);
            $em->flush();

            return $this->redirectToRoute('display_consultation');
        }
        return $this->render('consultation/createconsultation.html.twig',['f'=>$form->createView()]);

       
    }

    #[Route('/removeConsultation/{id}', name: 'supp_consultation')]
    public function suppressionConsultation(Consultation $consultation): Response
    {
        $em=$this->getDoctrine()->getManager();
        $em->remove($consultation);
        $em->flush();

        return $this->redirectToRoute('display_consultation');

    }

    #[Route('/modifConsultation{id}', name: 'modifConsultation')]
    public function modifActivite(Request $request,$id): Response
    {   
        $consultation= $this->getDoctrine()->getRepository(Consultation::class)->find($id);

        $form=$this->createForm(ConsultationType::class,$consultation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('display_consultation');
        }
        return $this->render('consultation/updateConsultation.html.twig',['f'=>$form->createView()]);
    }

    #[Route('/affichercons', name: 'afficher_cons')]
    public function indexAdminn(NormalizerInterface $normalizer)
    {
        $activites= $this->getDoctrine()->getManager()->getRepository(Consultation::class)->findAll();
        $jsonContent=$normalizer->normalize($activites,'json',['groups'=>'Consultation']);
           
        return new Response(json_encode($jsonContent)); 
    
    }

    /**
     * @Route("/ajoutercons", name="ajouterccons")
     * @Method("POST")
     */

    public function ajoutercons(Request $request,NormalizerInterface $normalizer)
    {
        $activite = new Consultation() ;
       
        $datedeconsultation =  new \DateTime('now');
        $image=$request->query->get("image");
        $em = $this->getDoctrine()->getManager();
        $activite->setDatedeconsultation($datedeconsultation);
        $activite->setImage($image);
        $em->persist($activite);
        $em->flush();
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Consultation']);
           
        return new Response(json_encode($jsonContent)); 

    }


    /**
     * @Route("/deletecons/{id}", name="delete_cons")
     */

     public function deleteReclamationAction(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $activite = $em->getRepository(Consultation::class)->find($id);
        if($activite!=null ) {
            $em->remove($activite);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Consultation']);
           
        return new Response(json_encode($jsonContent)); 

    }
      /**
     * @Route("/updateActivite/{id}", name="update_activite")
     */
     public function modifierReclamationAction(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $activite = $this->getDoctrine()->getManager()
            ->getRepository(Consultation::class)
            ->find($request->get("id"));



            
            $activite->setImage($request->get("image"));

        $em->persist($activite);
        $em->flush();
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Consultation']);
           
        return new Response(json_encode($jsonContent)); 
        

    }

     
/**
     * @Route("/calendar/ok", name="calendar", methods={"GET"})
     */
    public function calendar(ConsultationRepository $rdvRepository): Response
    {
        $events=$rdvRepository->findAll();
        $rdvs = [];

        foreach($events as $rdv){
            $rdvs[] = [

                'date' => $rdv->getDatedeconsultation()->format('Y-m-d H:i'),
                
               

                
               

            ];
        }
       

        $data = json_encode($rdvs);
       

        return $this->render('consultation/calendar.html.twig',compact('data'));

    }

    


}

<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

use App\Entity\Consultation;
use App\Form\ConsultationType;
use App\Repository\OrdonanceRepository;

use Symfony\Component\HttpFoundation\Request;



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

    #[Route('/admin', name: 'display_admin')]
    public function indexAdmin(): Response
    {
      
       
        return $this->render('admin/index.html.twig');
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













}

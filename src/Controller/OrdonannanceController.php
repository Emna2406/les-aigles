<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use App\Entity\Ordonance;
use App\Form\OrdonanceType;
use App\Form\EmailType;
use App\Repository\OrdonanceRepository;

use Symfony\Component\HttpFoundation\Request;



use Doctrine\ORM\EntityManagerInterface;

use Symfony\Component\Serializer\Serializer;

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
    public function suppressionConsultation(Ordonance $ordonance): Response
    {
        $em=$this->getDoctrine()->getManager();
        $em->remove($ordonance);
        $em->flush();

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

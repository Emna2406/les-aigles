<?php

namespace App\Controller;

use App\Entity\Service;
use App\Form\ServiceType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;

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
    public function index1(): Response
    {
        $services = $this->getDoctrine()->getRepository(Service::class)->findAll();
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
            $en->persist($service);
            $en->flush();
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

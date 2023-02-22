<?php

namespace App\Controller;

use App\Entity\Chambre;
use App\Entity\Service;
use App\Form\ChambreType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ChambreController extends AbstractController
{
    #[Route('/chambre', name: 'display_chambre')]
    public function index(): Response
    {
        $chambre = $this->getDoctrine()->getManager()->getRepository(Chambre::class)->findAll();
        return $this->render('chambre/index.html.twig', [
            'c'=>$chambre
        ]);
    }
    #[Route('/addChambre', name: 'addChambre')]
    public function addChambre(Request $request): Response
    {
        $chambre = new Chambre();
        $form = $this->createForm(ChambreType::class,$chambre);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){

            $en = $this->getDoctrine()->getManager();
            $en->persist($chambre);
            $en->flush();
            return $this->redirectToRoute('display_chambre');

        }
        return $this->render('chambre/createChambre.html.twig',['f'=>$form->createView()]);



    }
    #[Route('/removeChambre/{id}', name: 'supp_chambre')]
    public function SuppresionChambre( Chambre $chambre): Response
    {
        $en = $this->getDoctrine()->getManager();
        $en->remove($chambre);
        $en->flush();
        return $this->redirectToRoute('display_chambre');

    }
    #[Route('/modifChambre/{id}', name: 'modifChambre')]
    public function modifChambre(Request $request,$id): Response
    {
        $chambre = $this->getDoctrine()->getManager()->getRepository(Chambre::class)->find($id);
        $form = $this->createForm(ChambreType::class,$chambre);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){

            $en = $this->getDoctrine()->getManager();
            $en->flush();
            return $this->redirectToRoute('display_chambre');

        }
        return $this->render('chambre/updateChambre.html.twig',['f'=>$form->createView()]);



    }

}

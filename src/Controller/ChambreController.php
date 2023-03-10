<?php

namespace App\Controller;

use App\Entity\Chambre;
use App\Entity\Service;
use App\Form\ChambreType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
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
    /**
     * @Route("/addChbr", name="add_chbr")
     * @Method("POST")
     */

    public function ajouterServiceAction(Request $request,NormalizerInterface $normalizer)
    {
        $service = new Chambre() ;
        $num = $request->query->get("num");

        $capacite= $request->query->get("capacite");
        $prix= $request->query->get("prix");
        $etat= $request->query->get("etat");
        $em = $this->getDoctrine()->getManager();


        $service->setNum($num);
        $service->setCapacite($capacite);
        $service->setPrix($prix);
        $service->setEtat($etat);


        $em->persist($service);
        $em->flush();
        $jsonContent=$normalizer->normalize($service,'json',['groups'=>'Chambre']);

        return new Response(json_encode($jsonContent));

    }
    /**
     * @Route("/deletech/{id}", name="delete_ch")
     */

    public function deleteServiceAction(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $chambre = $em->getRepository(Chambre::class)->find($id);
        if($chambre!=null ) {
            $em->remove($chambre);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($chambre,'json',['groups'=>'Chambre']);

        return new Response(json_encode($jsonContent));

    }

    /**
     * @Route("/updatech/{id}", name="update_ch")
     */
    public function modifierServiceAction(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $chambre = $this->getDoctrine()->getManager()
            ->getRepository(Chambre::class)
            ->find($request->get("id"));



        $chambre->setNum($request->get("num"));
        $chambre->setCapacite($request->get("capacite"));
        $chambre->setPrix($request->get("prix"));
        $chambre->setEtat($request->get("etat"));

        $em->persist($chambre);
        $em->flush();
        $jsonContent=$normalizer->normalize($chambre,'json',['groups'=>'Chambre']);

        return new Response(json_encode($jsonContent));


    }
    /**
     * @Route("/Displaychjson", name="display_chj")
     */
    public function indexAdminn(NormalizerInterface $normalizer)
    {
        $service= $this->getDoctrine()->getManager()->getRepository(Chambre::class)->findAll();
        $jsonContent=$normalizer->normalize($service,'json',['groups'=>'Chambre']);

        return new Response(json_encode($jsonContent));

    }


}

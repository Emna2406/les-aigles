<?php

namespace App\Controller;

use App\Entity\Type;
use App\Entity\User;
use App\Form\Types;
use App\Form\UserType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class TypeController extends AbstractController
{
    /**
     * @Route("/type", name="displayType")
     */
    public function index(): Response
    {
        $type = $this->getDoctrine()->getManager()->getRepository(Type::class)->findAll();
        return $this->render('admin/type/index.html.twig', [
            't' => $type
        ]);
    }



    /**
     * @Route("/addtype", name="addtype")
     */
    public function addType(Request $request): Response
    {
        $type = new Type();

        $form = $this->createForm(Types::class,$type);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($type);//Add
            $em->flush();

            return $this->redirectToRoute('displayType');
        }
        return $this->render('admin/type/CreateTypeUser.html.twig',['t'=>$form->createView()]);
    }

    /**
     * @Route("/removetype/{id}", name="supp_type")
     */
    public function suppressionType(Type  $type): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($type);
        $em->flush();

        return $this->redirectToRoute('displayType');


    }

    /**
     * @Route("/modiftype/{id}", name="modiftype")
     */
    public function modiftype(Request $request,$id): Response
    {
        $type = $this->getDoctrine()->getManager()->getRepository(Type::class)->find($id);

        $form = $this->createForm(Types::class,$type);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('displayType');
        }
        return $this->render('admin/type/updatetype.html.twig',['t'=>$form->createView()]);
    }



}

<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\UserType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class UserController extends AbstractController
{
    /**
     * @Route("/user", name="displayadmin")
     */
    public function index(): Response
    {
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->findAll();
        return $this->render('admin/user/index.html.twig', [
            'b'=>$user
        ]);
    }

    /**
     * @Route("/adduser", name="adduser")
     */
    public function addUser(Request $request): Response
    {
        $user = new User();

        $form = $this->createForm(UserType::class,$user);


        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);//Add
            $em->flush();

            return $this->redirectToRoute('displayadmin');
        }
        return $this->render('admin/user/createUser.html.twig',['f'=>$form->createView()]);
    }

    /**
     * @Route("/removeuser/{id}", name="supp_user")
     */
    public function suppressionUser(User  $user): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();

        return $this->redirectToRoute('displayadmin');


    }

    /**
     * @Route("/modifuser/{id}", name="modifuser")
     */
    public function modifuser(Request $request,$id): Response
    {
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->find($id);

        $form = $this->createForm(UserType::class,$user);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('displayadmin');
        }
        return $this->render('admin/user/updateuser.html.twig',['f'=>$form->createView()]);
    }



}

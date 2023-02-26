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
     * @Route("/user", name="display")
     */
    public function index(): Response
    {
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->findAll();
        return $this->render('user/index.html.twig', [
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

            return $this->redirectToRoute('display');
        }
        return $this->render('user/createUser.html.twig',['f'=>$form->createView()]);
    }

    /**
     * @Route("/removeuser/{id}", name="supp_user")
     */
    public function suppressionUser(User  $user): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();

        return $this->redirectToRoute('display');


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

            return $this->redirectToRoute('display');
        }
        return $this->render('user/updateuser.html.twig',['f'=>$form->createView()]);
    }


    /************************************************Front****************************************************************/
    /**
     * @Route("/", name="frontdisplay")
     */
    public function indexFront(): Response
    {
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->findAll();
        return $this->render('userFront/index.html.twig', [
            'b'=>$user
        ]);
    }

    /**
     * @Route("/frontadduser", name="frontadduser")
     */
    public function frontaddUser(Request $request): Response
    {
        $user = new User();

        $form = $this->createForm(UserType::class,$user);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);//Add
            $em->flush();

            return $this->redirectToRoute('frontdisplay');
        }
        return $this->render('userFront/createUser.html.twig',['f'=>$form->createView()]);
    }

    /**
     * @Route("/frontremoveuser/{id}", name="front_supp_user")
     */
    public function frontsuppressionUser(User  $user): Response
    {
        $em = $this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();

        return $this->redirectToRoute('frontdisplay');


    }

    /**
     * @Route("/frontmodifuser/{id}", name="frontmodifuser")
     */
    public function frontmodifuser(Request $request,$id): Response
    {
        $user = $this->getDoctrine()->getManager()->getRepository(User::class)->find($id);

        $form = $this->createForm(UserType::class,$user);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return $this->redirectToRoute('frontdisplay');
        }
        return $this->render('user/updateuser.html.twig',['f'=>$form->createView()]);
    }

}

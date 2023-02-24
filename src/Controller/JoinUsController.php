<?php

namespace App\Controller;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/home')]
class JoinUsController extends AbstractController
{
  
    #[Route('/JoinUs', name: 'app_Check', methods: ['GET'])]
    public function index(): Response
    {
        return $this->render('home/check.html.twig');
    }

    #[Route('/join-us', name: 'app_JoinUs')]
    public function show(): Response
    {
        return $this->render('base1.html.twig', [
            'controller_name' => 'JoinUsController',
        ]);
    }
   
    

    
}
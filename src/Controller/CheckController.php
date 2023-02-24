<?php

namespace App\Controller;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/check-home')]
class CheckController extends AbstractController
{
  

   
   
    #[Route('/Check', name: 'app_check', methods: ['GET'])]
    public function index(): Response
    {
        return $this->render('home/check.html.twig', [
            'controller_name' => 'CheckController',
        ]);
    }

    
}
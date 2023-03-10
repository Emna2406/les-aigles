<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ApplyController extends AbstractController
{
    #[Route('/apply', name: 'app_apply')]
    public function index(): Response
    {
        return $this->render('base1.html.twig', [
            'controller_name' => 'ApplyController',
        ]);
    }

    #[Route('/notfound', name : 'not_found')]
    public function notFound(): Response
    {
        return $this->render('error_pages/not_found.html.twig');
    }
}

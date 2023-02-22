<?php

namespace App\Controller;
use App\Entity\Certificat;
use App\Form\CertificatType;
use App\Repository\CertificatRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;


class CertificatController extends AbstractController
{
    #[Route('/certificat', name: 'display_certificat')]
    public function index(): Response
    {
        $certificat= $this->getDoctrine()->getManager()->getRepository(Certificat::class)->findAll();
        return $this->render('certificat/index.html.twig', [
            'c'=>$certificat
         ]);
    }

    #[Route('/frontcertificat', name: 'display_certificat_front')]
    public function index2(): Response
    {
        $certificat= $this->getDoctrine()->getManager()->getRepository(Certificat::class)->findAll();
        return $this->render('certificat/indexfront.html.twig', [
            'cc'=>$certificat
         ]);
    }

    #[Route('/addcertificat', name: 'addcertificat')]
    public function addconsultation(Request $request): Response
    {
        $certificat=new Certificat(); 
        $form=$this->createForm(CertificatType::class,$certificat);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
           
           
            $em=$this->getDoctrine()->getManager();
            $em->persist($certificat);
            $em->flush();

            return $this->redirectToRoute('display_certificat');
        }
        return $this->render('certificat/createcertificat.html.twig',['f'=>$form->createView()]);

       
    }

    #[Route('/removeCertificat/{id}', name: 'supp_certificat')]
    public function suppressionCertificat(Certificat $certificat): Response
    {
        $em=$this->getDoctrine()->getManager();
        $em->remove($certificat);
        $em->flush();

        return $this->redirectToRoute('display_certificat');

    }

    #[Route('/modifCertificat{id}', name: 'modifCertificat')]
    public function modifCertificat(Request $request,$id): Response
    {   
        $certificat= $this->getDoctrine()->getRepository(Certificat::class)->find($id);

        $form=$this->createForm(CertificatType::class,$certificat);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid())
        {
            $em=$this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('display_certificat');
        }
        return $this->render('certificat/updateCertificat.html.twig',['f'=>$form->createView()]);
    }




}

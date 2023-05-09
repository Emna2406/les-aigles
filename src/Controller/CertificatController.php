<?php

namespace App\Controller;
use App\Entity\Certificat;
use App\Form\CertificatType;
use Dompdf\Options;
use App\Repository\CertificatRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Dompdf\Dompdf;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use symfony\component\HttpFoundation\JsonResponse;
use symfony\component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;










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
 
 
    #[Route('/affichercert', name: 'afficher_certificat')]
    public function indexAdminn(NormalizerInterface $normalizer)
    {
        $activites= $this->getDoctrine()->getManager()->getRepository(Certificat::class)->findAll();
        $jsonContent=$normalizer->normalize($activites,'json',['groups'=>'Certificat']);
           
        return new Response(json_encode($jsonContent)); 
    
    }

/**
     * @Route("/ajoutercert", name="ajoutercertt")
     * @Method("POST")
     */

     public function ajouterrdvj(Request $request,NormalizerInterface $normalizer)
     {
         $activite = new Certificat() ;
        
         $datedeb =  new \DateTime('now');
         $datefin =  new \DateTime('now');
         $image=$request->query->get("image");
         $em = $this->getDoctrine()->getManager();
         $activite->setDatedeb($datedeb);
         $activite->setDatefin($datefin);
         $activite->setImage($image);
         $em->persist($activite);
         $em->flush();
         $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Certificat']);
            
         return new Response(json_encode($jsonContent)); 
 
     }
      /**
     * @Route("/deletecert/{id}", name="delete_cert")
     */

    public function deleteReclamationAction(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $activite = $em->getRepository(Certificat::class)->find($id);
        if($activite!=null ) {
            $em->remove($activite);
            $em->flush();

        }
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Certificat']);
           
        return new Response(json_encode($jsonContent)); 

    }

     /**
     * @Route("/updatecert/{id}", name="update_cert")
     */
    public function modifierReclamationAction(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $activite = $this->getDoctrine()->getManager()
            ->getRepository(Certificat::class)
            ->find($request->get("id"));



            
            $activite->setImage($request->get("image"));

        $em->persist($activite);
        $em->flush();
        $jsonContent=$normalizer->normalize($activite,'json',['groups'=>'Certificat']);
           
        return new Response(json_encode($jsonContent)); 
        

    }

     



 
     }
  


    
    
    

    





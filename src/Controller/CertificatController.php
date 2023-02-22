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
 
/**
     * @Route("/listrdvpdf", name="listpdf")
     */
    public function listpdf(Request $request)
    {

        $entityManager = $this->getDoctrine();
        $list = $entityManager->getRepository(Certificat::class)
            ->findAll();


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        //$pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $html=$this->render('certificat/listpdf.html.twig', [
            'listpdf' => $list
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html.'lklkkl');

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        
        $dompdf->stream("certificat/listpdf.html.twig", [
            "Attachment" => false
        ]);


    }

}

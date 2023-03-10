<?php

namespace App\Controller;

use MercurySeries\FlashyBundle\FlashyNotifier;
use Normalizer;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;
use App\Entity\Produit;
use App\Form\ProduitType;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\Material\BarChart;

#[Route('/produit')]
class ProduitController extends AbstractController
{
    #[Route('/', name: 'app_produit_index', methods: ['GET'])]
    public function index(Request $request, ProduitRepository $produitRepository): Response
    {
        $query = $request->query->get('q');
        if ($query) {
            $produits = $produitRepository->searchProduitByString($query);
        } else {
            $produits = $produitRepository->findAll();
        }
    
        return $this->render('produit/index.html.twig', [
            'produits' => $produits,
            'query' => $query,
        ]);
    }

    #[Route('/new', name: 'app_produit_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ProduitRepository $produitRepository): Response
    {
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);
 
        if ($form->isSubmitted() && $form->isValid()) {
            $produitRepository->save($produit, true);
          

            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }

    #[Route('/show/{id}', name: 'app_produit_show', methods: ['GET'])]
    
    public function show(Produit $produit): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_produit_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Produit $produit, ProduitRepository $produitRepository): Response
    {
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $produitRepository->save($produit, true);

            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }
    #[Route('/notfound', name : 'not_found')]
    public function notFound(): Response
    {
        return $this->render('error_pages/403PR.html.twig');
    }
   
 /*   #[Route('/{id}', name: 'app_produit_delete', methods: ['POST'])]
   // public function delete(Request $request, Produit $produit, ProduitRepository $produitRepository): Response
    {
        if ($this->isCsrfTokenValid('delete' . $produit->getId(), $request->request->get('_token'))) {
            $produitRepository->remove($produit, true);
        }

        return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
    }*/

  
    #[Route('/{id}', name: 'app_produit_delete')]
    public function suppressionConsultation(Produit $consultation): Response
    {
        $em=$this->getDoctrine()->getManager();
        $em->remove($consultation);
        $em->flush();


        $mail = new PHPMailer(true);
           

        try {
            
            
            

            
         
           

            //Server settings
            $mail->SMTPDebug = SMTP::DEBUG_SERVER;
            $mail->isSMTP();
            $mail->Host       = 'smtp.gmail.com';
            $mail->SMTPAuth   = true;
             
            $mail->Username   = 'emna.baccar@esprit.tn';            
            $mail->Password   = '223JFT3808';                         // SMTP password
            $mail->SMTPSecure = PHPMailer::ENCRYPTION_STARTTLS;
            $mail->Port       = 587;


            // email pour dire a le docteur que une reservation a ete annuler (mouchar)

            //Recipients
            $mail->addAddress('emna.baccar@esprit.tn');     // Add a recipient
            // Content
            $mail->isHTML(true);                                  // Set email format to HTML
            $mail->Subject = " Alert suppression d une Produit  ";
            $mail->Body    = "Produit supprimer   !!!!!!!  ";

            $mail->send();
            $this->addFlash('message','the email has been sent');

            

        } catch (Exception $e) {
            echo "Message could not be sent. Mailer Error: {$mail->ErrorInfo}";
        }

        return $this->redirectToRoute('app_produit_index');

    }








    #[Route('/ajouterProd', name: 'ajouterProd')]

    public function ajouterProd(Request $request, NormalizerInterface $normalizer, ProduitRepository $rep)
    {
        $produit = new Produit();
        $nom = $request->query->get("nom");
        $stock = $request->query->get("stock");





        $produit->setNom($nom);
        $produit->setStock($stock);
        $rep->save($produit, true);
        $jsonContent = $normalizer->normalize($produit, 'json', ['groups' => 'produit']);

        return new Response(json_encode($jsonContent));
    }




    #[Route('/updateproduit/{id}', name: 'update_produit')]

    public function modifierProduitAction(Request $request, NormalizerInterface $normalizer)
    {
        $em = $this->getDoctrine()->getManager();
        $activite = $this->getDoctrine()->getManager()
            ->getRepository(Activite::class)
            ->find($request->get("id"));
        $activite->setNom($request->get("nom"));
        $activite->setStock($request->get("stock"));


        $em->persist($activite);
        $em->flush();
        $jsonContent = $normalizer->normalize($activite, 'json', ['groups' => 'produit']);

        return new Response(json_encode($jsonContent));
    }

    #[Route('/deleteProd/{id}', name: 'delete_Prod')]
    public function deleteProd(Request $request, NormalizerInterface $normalizer, $id)
    {
        $em = $this->getDoctrine()->getManager();
        $produit = $em->getRepository(Produit::class)->find($id);
        if ($produit != null) {
            $em->remove($produit);
            $em->flush();
        }
        $jsonContent = $normalizer->normalize($produit, 'json', ['groups' => 'produit']);

        return new Response(json_encode($jsonContent));
    }
    #[Route('/displayProd', name: 'displayProd')]
    public function displayProd(NormalizerInterface $normalizer)
    {
        $produits = $this->getDoctrine()->getManager()->getRepository(Produit::class)->findAll();
        $jsonContent = $normalizer->normalize($produits, 'json', ['groups' => 'produit']);

        return new Response(json_encode($jsonContent));
    }
}

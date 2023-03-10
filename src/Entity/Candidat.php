<?php

namespace App\Entity;

use App\Repository\CandidatRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Candidat
 *
 * @ORM\Table(name="candidat", indexes={@ORM\Index(name="id_Offre", columns={"id_Offre"})})
 * @ORM\Entity
 */
#[ORM\Entity(repositoryClass: CandidatRepository::class)]
class Candidat
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    /**
     * @var int
     *
     * @ORM\Column(name="id_cand", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */


        /** 
        *@Groups("Candidat")
        */
    private $idCand;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=50, nullable=false)
     */


     #[ORM\Column(length: 255)]
     /**
      * @Assert\NotBlank
      * @Assert\Length(min=4, max=15)
      *     pattern="/^[a-zA-Z0-9\s]+$/",
      *     message="Le nom du candidat ne peut contenir que des lettres, des chiffres et des espaces"
      * )
      */

      /** 
        *@Groups("Candidat")
        */


    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=50, nullable=false)
     */


    #[ORM\Column(length: 255)]
     /**
      * @Assert\NotBlank
      * @Assert\Length(min=3, max=15)
      *     pattern="/^[a-zA-Z0-9\s]+$/",
      *     message="Le prenom du candidat ne peut contenir que des lettres, des chiffres et des espaces"
      * )
      */


      /** 
        *@Groups("Candidat")
        */
    private $prenom;

    /**
     * @var string
     *
     * @ORM\Column(name="cv", type="text", length=65535, nullable=false)
     */
    #[ORM\Column(length: 255)]

    /** 
        *@Groups("Candidat")
        */
        private $cv;

    /**
     * @var string
     *
     * @ORM\Column(name="email", type="string", length=50, nullable=false)
     */


     #[ORM\Column(length: 255)]
    /**
     * @Assert\NotBlank
     * @Assert\Length(min=4, max=20)
     *     pattern="/^[a-zA-Z0-9\s]+$/",
     *     message="L'email du candidat ne peut contenir que des lettres, des chiffres et des espaces"
     * )
     */

     /** 
        *@Groups("Candidat")
        */

    private $email;

    /**
     * @var int
     *
     * @ORM\Column(name="id_Offre", type="integer", nullable=false)
     */

     #[ORM\Column(length: 255)]


     /** 
        *@Groups("Candidat")
        */
    private $idService;


   
  
    public function getidCand(): ?int
    {
        return $this->idCand;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }
    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }
    public function getCV(): ?string
    {
        return $this->cv;
    }

    public function setCV(string $cv): self
    {
        $this->cv = $cv;

        return $this;
    }
    public function getIdservice(): ?string
    {
        return $this->idService;
    }

    public function setIdservice(int $idService): self
    {
        $this->idService = $idService;

        return $this;
    }


}

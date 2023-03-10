<?php

namespace App\Entity;

use App\Repository\RdvRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;
#[ORM\Entity(repositoryClass: RdvRepository::class)]
class Rdv
{
   
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
   
    private ?int $idrdv = null;
 /**
      *  @Assert\NotBlank(message="date de reservation de patient est obligatoire")
     * @Groups("rdv")
     */
   
    #[ORM\Column(type: Types::DATETIME_MUTABLE)]
    private ?\DateTimeInterface $date = null;
   
     /**
      *  @Assert\NotBlank(message="Nom de patient est obligatoire")
     * @Assert\NotBlank(message="NB: nom patient  doit etre Obligatoire et min caractere 7 et max 99")
     * @Assert\Length(
     *      min = 7,
     *      max = 100,
     *      minMessage = "doit etre >=4 ",
     *      maxMessage = "doit etre <=100" )
     * @Groups("rdv")
     */
    
    #[ORM\Column(length: 255)]
    private ?string $nompatient = null;
    /** 
    *@Groups("rdv")
    */

    #[ORM\Column(length: 255)]
    private ?string $numtel = null;
    
     /**
      *  @Assert\NotBlank(message="message de patient est obligatoire")
     * @Assert\NotBlank(message="NB: nom patient  doit etre Obligatoire et min caractere 2 et max 99")
     * @Assert\Length(
     *      min = 2,
     *      max = 100,
     *      minMessage = "doit etre >=4 ",
     *      maxMessage = "doit etre <=100" )
     *   *@Groups("rdv")
     */

    #[ORM\Column(type: Types::TEXT)]
    private ?string $message = null;
    
     /**
      *  @Assert\NotBlank(message="email de patient est obligatoire")
     * @Assert\NotBlank(message="NB: nom patient  doit etre Obligatoire et min  caractere 5 et max 99")
     * @Assert\Length(
     *      min = 5,
     *      max = 100,
     *      minMessage = "doit etre >=4 ",
     *      maxMessage = "doit etre <=100" )
     *   *@Groups("rdv")
     */

    #[ORM\Column(type: Types::TEXT)]
    private ?string $adr = null;



   


   

    public function getIdrdv(): ?int
    {
        return $this->idrdv;
    }

    

    

    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

        return $this;
    }

    public function getNompatient(): ?string
    {
        return $this->nompatient;
    }

    public function setNompatient(string $nompatient): self
    {
        $this->nompatient = $nompatient;

        return $this;
    }


    public function getNumtel(): ?string
    {
        return $this->numtel;
    }

    public function setNumtel(string $numtel): self
    {
        $this->numtel = $numtel;

        return $this;
    }

    public function getMessage(): ?string
    {
        return $this->message;
    }

    public function setMessage(string $message): self
    {
        $this->message = $message;

        return $this;
    }

    public function getAdr(): ?string
    {
        return $this->adr;
    }

    public function setAdr(string $adr): self
    {
        $this->adr = $adr;

        return $this;
    }

   

}

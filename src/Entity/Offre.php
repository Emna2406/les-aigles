<?php

namespace App\Entity;

use App\Repository\OffreRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use ConsoleTVs\Profanity\Builder;
use ConsoleTVs\Profanity\Facades\Profanity;

/**
 * Offre
 *
 * @ORM\Table(name="offre", indexes={@ORM\Index(name="id_service", columns={"id_service"})})
 * @ORM\Entity
 */
#[ORM\Entity(repositoryClass: OffreRepository::class)]
class Offre
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
   


        /** 
    *@Groups("Offre")
    */
   
    /**
     * @var int
     *
     * @ORM\Column(name="id_Offre", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    #[ORM\Column(length: 255)]

        /** 
    *@Groups("Offre")
    */
        private $id;

 /**
     * @var int
     *
     * @ORM\Column(name="nbr_places", type="integer", nullable=false)
     */
    #[ORM\Column(length: 255)]

        /** 
    *@Groups("Offre")
    */
        private $nbrPlaces;

          
        
        
    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text", length=65535, nullable=false)
     */

     #[ORM\Column(length: 255)]
     /** 
 
 */

  /**
   * *@Groups("Offre")
   * @Assert\NotBlank
   * @Assert\Length(min=4, max=15)
   *     pattern="/^[a-zA-Z0-9\s]+$/",
   *     message="La description de l'offre ne peut contenir que des lettres, des chiffres et des espaces"
   * )
   */
       private $description;
    /**
     * @var int
     *
     * @ORM\Column(name="id_service", type="integer", nullable=false)
     */
    #[ORM\Column(length: 255)]


     /** 
    *@Groups("Offre")
    */
    private $idService;

     


         
   
      

    public function getid(): ?string
    {
        return $this->id;
    }

   

    public function getnbrPlaces(): ?string
    {
        return $this->nbrPlaces;
    }
    
    public function setnbrPlaces(string $nbrPlaces): self
    {
        $this->nbrPlaces = $nbrPlaces;

        return $this;
    }


    public function getdescription(): ?string
    {
        return $this->description;
    }

    public function setdescription(string $description): self
    {
        $clean_words = \ConsoleTVs\Profanity\Builder::blocker($description)->filter();
        $this->description = $clean_words;
        return $this;
    }

    public function getidService(): ?string
    {
        return $this->idService;
    }

    public function setidService(string $idService): self
    {
        $this->idService = $idService;

        return $this;
    }
    


  
    

}

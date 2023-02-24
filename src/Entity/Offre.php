<?php

namespace App\Entity;

use App\Repository\OffreRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

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
     * @var int
     *
     * @ORM\Column(name="id_Offre", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    #[ORM\Column(length: 255)]
        private $idOffre;

    /**
     * @var int
     *
     * @ORM\Column(name="id_service", type="integer", nullable=false)
     */
    #[ORM\Column(length: 255)]
    private $idService;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text", length=65535, nullable=false)
     */

     #[ORM\Column(length: 255)]


     /**
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
     * @ORM\Column(name="nbr_places", type="integer", nullable=false)
     */
    #[ORM\Column(length: 255)]
        private $nbrPlaces;



    public function getidOffre(): ?int
    {
        return $this->idOffre;
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

    public function getdescription(): ?string
    {
        return $this->description;
    }

    public function setdescription(string $description): self
    {
        $this->description = $description;

        return $this;
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
    




}

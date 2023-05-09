<?php

namespace App\Entity;


use Doctrine\ORM\Mapping as ORM;
use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: ProduitRepository::class)]
class Produit
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups('produit')]
    /**
     * @Groups("produit")
     */
    private ?int $id = null;

    #[ORM\Column(length: 255)]

    /**
     * @Assert\NotBlank
     * @Assert\Length(min=4, max=15)
     *     pattern="/^[a-zA-Z0-9\s]+$/",
     *     message="Le nom du produit ne peut contenir que des lettres, des chiffres et des espaces"
     * )
     *  /**
     * @Groups("produit")
     */

    private ?string $nom = null;

    #[ORM\Column]

    /**
     * @Assert\NotBlank
     *  *  /**
     * @Groups("produit")
    
     */
    private ?int $stock = null;

    /**
     * @ORM\OneToMany(targetEntity="Partenaire", mappedBy="produit")
     *  *  /**
     */
    private $partenaires;

    /**
     * @ORM\Column(type="string")
     *  *  /**
     * @Groups("produit")
     */
    private $nom_partenaire;

    public function __construct()
    {
        $this->partenaires = new ArrayCollection();
    }
    public function getId(): ?int
    {
        return $this->id;
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

    public function getStock(): ?int
    {
        return $this->stock;
    }

    public function setStock(int $stock): self
    {
        $this->stock = $stock;

        return $this;
    }

    public function getNomPartenaire(): ?Partenaire
    {
        return $this->nom_partenaire;
    }

    public function setNomPartenaire(?Partenaire $nom_partenaire): self
    {
        $this->nom_partenaire = $nom_partenaire;

        return $this;
    }
}

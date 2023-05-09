<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\PartenaireRepository;
use Doctrine\Common\Collections\Collection;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: PartenaireRepository::class)]
class Partenaire
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups('partenaire')]
    /**
     * @Groups("partenaire")
     */
    private ?int $id = null;

    #[ORM\Column(length: 255)]

    /**
     *
     * @Assert\NotBlank
     * @Assert\Length(min=4, max=15)
     *     pattern="/^[a-zA-Z0-9\s]+$/",
     *     message="Le nom du partenaire ne peut contenir que des lettres, des chiffres et des espaces"
     * )
     *  * @Groups("partenaire")
     */
    private ?string $nom = null;


    #[ORM\Column(length: 255)]

    /** 
     * 
     * @Assert\NotBlank
     * @Assert\Length(min=4, max=20)
     *     pattern="/^[a-zA-Z0-9\s]+$/",
    
     *     message="L'email du partenaire ne peut contenir que des lettres, des chiffres et des espaces"
     * )
     *  * @Groups("partenaire")
     */


    private ?string $email = null;
    /**
     *
     * @ORM\ManyToOne(targetEntity="Produit", inversedBy="partenaires")
     * @ORM\JoinColumn(name="produit_id", referencedColumnName="id")
     *  * @Groups("partenaire")
     */
    private $produit;

    /**
     * 
     * @ORM\Column(type="string")
     *  * @Groups("partenaire")
     */
    private $nomPartenaire;








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

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }
}

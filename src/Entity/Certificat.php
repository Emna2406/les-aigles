<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Certificat
 *
 * @ORM\Table(name="certificat", indexes={@ORM\Index(name="iduser", columns={"iduser"})})
 * @ORM\Entity
 */
class Certificat
{
    /**
     * @var int
     *
     * @ORM\Column(name="idcerti", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idcerti;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_debut", type="date", nullable=false)
     */
    private $dateDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_expiration", type="date", nullable=false)
     */
    private $dateExpiration;

    /**
     * @var string
     *
     * @ORM\Column(name="image", type="text", length=65535, nullable=false)
     */
    private $image;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="iduser", referencedColumnName="iduser")
     * })
     */
    private $iduser;


}

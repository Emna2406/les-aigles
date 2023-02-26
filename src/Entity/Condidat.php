<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Condidat
 *
 * @ORM\Table(name="condidat", indexes={@ORM\Index(name="id_offre", columns={"id_offre"})})
 * @ORM\Entity
 */
class Condidat
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_cond", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idCond;

    /**
     * @var string
     *
     * @ORM\Column(name="nom", type="string", length=50, nullable=false)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="prenom", type="string", length=50, nullable=false)
     */
    private $prenom;

    /**
     * @var string
     *
     * @ORM\Column(name="cv", type="text", length=65535, nullable=false)
     */
    private $cv;

    /**
     * @var string
     *
     * @ORM\Column(name="email", type="string", length=50, nullable=false)
     */
    private $email;

    /**
     * @var \Offre
     *
     * @ORM\ManyToOne(targetEntity="Offre")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_offre", referencedColumnName="id_offre")
     * })
     */
    private $idOffre;


}

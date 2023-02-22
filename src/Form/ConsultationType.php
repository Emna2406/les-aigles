<?php

namespace App\Form;

use App\Entity\Consultation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

use Symfony\Component\Console\Command\ListCommand;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use App\Entity\Medecin;
use App\Entity\Patient;
use phpDocumentor\Reflection\PseudoTypes\LiteralString;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class ConsultationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('medecin',EntityType::class,[
            'class'=>Medecin::class,'choice_label'=>'nom'
        ])
        ->add('patient',EntityType::class,[
            'class'=>Patient::class,'choice_label'=>'nom'
        ])
            ->add('datedeconsultation')
            ->add('image',FileType::class,array('data_class' => null,'required' => false))
            ->add('ajouter',SubmitType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Consultation::class,
        ]);
    }
}

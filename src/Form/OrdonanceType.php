<?php

namespace App\Form;

use App\Entity\Ordonance;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use App\Entity\Consultation;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use phpDocumentor\Reflection\PseudoTypes\LiteralString;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Console\Command\ListCommand;

class OrdonanceType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('consultation',EntityType::class,[
            'class'=>Consultation::class,'choice_label'=>'id'
        ])
            ->add('date')
            ->add('description')
            ->add('image',FileType::class,array('data_class' => null,'required' => false))
            ->add('ajouter',SubmitType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Ordonance::class,
        ]);
    }
}

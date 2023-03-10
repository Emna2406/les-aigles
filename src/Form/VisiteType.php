<?php

namespace App\Form;
use App\Entity\Medecin;
use App\Entity\Patient;
use App\Entity\Visite;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TimeType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Validator\Constraints\File;

use Symfony\Component\Form\Extension\Core\Type\FileType;
use App\Controller\SluggerInterface;





use Symfony\Bridge\Doctrine\Form\Type\EntityType;
class VisiteType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            
            ->add('nomvisiteur')
            ->add('patient',EntityType::class,[
                'class'=>Patient::class,'choice_label'=>'nom'
            ])
            ->add('nompatient')
            ->add('date', DateTimeType::class, [
                'date_widget' => 'single_text',
            ])
            ->add('numerotele')
            ->add('message')
            
        
            ->add('ajouter', SubmitType::class)
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Visite::class,
        ]);
    }
}

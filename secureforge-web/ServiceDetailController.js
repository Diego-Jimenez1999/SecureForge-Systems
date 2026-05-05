/**
 * Controlador para mostrar detalles de servicios en un modal.
 */
export default class ServiceDetailController {
    constructor() {
        this.modal = document.getElementById('service-detail-modal');
        this.closeBtn = document.getElementById('close-service-modal');
        this.titleElement = document.getElementById('service-detail-title');
        this.descriptionElement = document.getElementById('service-detail-description');
        this.benefitsList = document.getElementById('service-detail-benefits');
        this.useCasesList = document.getElementById('service-detail-use-cases');
        this.ctaLink = document.getElementById('service-detail-cta');

        // Datos de ejemplo para servicios (idealmente vendrían de un modelo o API)
        this.servicesData = [
            {
                id: 'desarrollo-medida',
                title: 'Desarrollo a Medida',
                description: 'Creamos soluciones de software personalizadas que se ajustan perfectamente a los requisitos únicos de tu negocio, desde aplicaciones empresariales hasta sistemas complejos.',
                benefits: ['Optimización de procesos', 'Ventaja competitiva', 'Escalabilidad garantizada', 'Integración perfecta'],
                useCases: ['Automatización de flujos de trabajo', 'Plataformas de gestión interna', 'Herramientas específicas de la industria']
            },
            {
                id: 'auditoria-seguridad',
                title: 'Auditoría de Seguridad',
                description: 'Realizamos análisis exhaustivos de tus sistemas para identificar vulnerabilidades, evaluar riesgos y fortalecer tu postura de seguridad digital contra amenazas cibernéticas.',
                benefits: ['Identificación proactiva de riesgos', 'Cumplimiento normativo', 'Protección de datos sensibles', 'Recomendaciones de mejora'],
                useCases: ['Análisis de aplicaciones web', 'Evaluación de infraestructura de red', 'Pruebas de penetración']
            },
            {
                id: 'consultoria-tecnica',
                title: 'Consultoría Técnica',
                description: 'Ofrecemos asesoramiento experto en arquitectura de software, selección de tecnologías y estrategias de escalabilidad para asegurar el éxito y la longevidad de tus proyectos.',
                benefits: ['Decisiones tecnológicas informadas', 'Optimización de recursos', 'Reducción de riesgos técnicos', 'Planificación estratégica'],
                useCases: ['Diseño de arquitecturas cloud', 'Migración de sistemas legados', 'Estrategias de DevOps']
            }
        ];
    }

    init() {
        document.querySelectorAll('#servicios .p-8').forEach(card => {
            card.addEventListener('click', (e) => {
                const serviceId = e.currentTarget.querySelector('h3').textContent.toLowerCase().replace(/\s/g, '-');
                this.showServiceDetail(serviceId);
            });
        });
        if (this.closeBtn) {
            this.closeBtn.addEventListener('click', () => this.modal.classList.add('hidden'));
        }
    }

    showServiceDetail(serviceId) {
        const service = this.servicesData.find(s => s.id === serviceId);
        if (!service) return;
        this.titleElement.textContent = service.title;
        this.descriptionElement.textContent = service.description;
        this.benefitsList.innerHTML = service.benefits.map(b => `<li>${b}</li>`).join('');
        this.useCasesList.innerHTML = service.useCases.map(uc => `<li>${uc}</li>`).join('');
        this.modal.classList.remove('hidden');
    }
}
export default class ServiceDetailController {
    constructor() {
        this.modal = document.getElementById('service-detail-modal');
        this.closeBtn = document.getElementById('close-service-modal');
        this.titleElement = document.getElementById('service-detail-title');
        this.descriptionElement = document.getElementById('service-detail-description');
        this.benefitsList = document.getElementById('service-detail-benefits');
        this.useCasesList = document.getElementById('service-detail-use-cases');

        this.servicesData = [
            {
                id: 'desarrollo-a-medida',
                title: 'Desarrollo a Medida',
                description: 'Creamos soluciones de software personalizadas para procesos críticos de negocio.',
                benefits: ['Optimización de procesos', 'Escalabilidad', 'Integración con sistemas existentes'],
                useCases: ['ERP interno', 'Portal B2B', 'Automatización operativa']
            },
            {
                id: 'auditoria-de-seguridad',
                title: 'Auditoría de Seguridad',
                description: 'Evaluamos riesgos y fortalecemos aplicaciones, APIs e infraestructura.',
                benefits: ['Reducción de vulnerabilidades', 'Cumplimiento normativo', 'Hardening técnico'],
                useCases: ['Pentesting web', 'Revisión OWASP', 'Plan de remediación']
            },
            {
                id: 'consultoria-tecnica',
                title: 'Consultoría Técnica',
                description: 'Acompañamiento en arquitectura, escalabilidad y decisiones tecnológicas.',
                benefits: ['Menor deuda técnica', 'Decisiones informadas', 'Roadmap realista'],
                useCases: ['Migración de monolito', 'Diseño de APIs', 'Optimización de costos cloud']
            }
        ];
    }

    init() {
        const cards = document.querySelectorAll('#servicios .p-8');
        cards.forEach((card) => {
            card.addEventListener('click', () => {
                const title = card.querySelector('h3')?.textContent?.trim().toLowerCase() || '';
                const serviceId = title.replace(/\s+/g, '-');
                this.showServiceDetail(serviceId);
            });
        });

        if (this.closeBtn) {
            this.closeBtn.addEventListener('click', () => this.modal.classList.add('hidden'));
        }
    }

    showServiceDetail(serviceId) {
        const service = this.servicesData.find((s) => s.id === serviceId);
        if (!service || !this.modal) return;

        this.titleElement.textContent = service.title;
        this.descriptionElement.textContent = service.description;
        this.benefitsList.innerHTML = service.benefits.map((b) => `<li>${b}</li>`).join('');
        this.useCasesList.innerHTML = service.useCases.map((u) => `<li>${u}</li>`).join('');
        this.modal.classList.remove('hidden');
    }
}

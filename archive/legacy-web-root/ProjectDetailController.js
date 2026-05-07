import ProductModel from '../models/ProductModel.js'; // Reutilizar el modelo de productos

/**
 * Controlador para mostrar detalles de proyectos en un modal.
 */
export default class ProjectDetailController {
    constructor() {
        this.modal = document.getElementById('project-detail-modal');
        this.closeBtn = document.getElementById('close-project-modal');
        this.titleElement = document.getElementById('project-detail-title');
        this.descriptionElement = document.getElementById('project-detail-description');
        this.imagesContainer = document.getElementById('project-detail-images');
        this.architectureElement = document.getElementById('project-detail-architecture');
        this.techStackContainer = document.getElementById('project-detail-tech-stack');
        this.ctaLink = document.getElementById('project-detail-cta');
    }

    init() {
        // Delegación de eventos para los botones de "Ver Detalles" en las tarjetas de productos
        document.getElementById('products-container').addEventListener('click', (e) => {
            const detailButton = e.target.closest('.btn-detail-project');
            if (detailButton) {
                const productId = detailButton.getAttribute('data-id');
                this.showProjectDetail(parseInt(productId));
            }
        });

        if (this.closeBtn) {
            this.closeBtn.addEventListener('click', () => this.modal.classList.add('hidden'));
        }
    }

    showProjectDetail(productId) {
        const project = ProductModel.getAll().find(p => p.id === productId);
        if (!project) return;

        this.titleElement.textContent = project.title;
        this.descriptionElement.textContent = project.description;

        // Imágenes (ejemplo, si tu modelo tuviera un array de URLs de imágenes)
        this.imagesContainer.innerHTML = project.images ? project.images.map(imgSrc => `<img src="${imgSrc}" alt="${project.title}" class="rounded-lg shadow-md">`).join('') : '';
        if (!project.images || project.images.length === 0) {
            this.imagesContainer.innerHTML = '<p class="text-brand-light/60 col-span-full">No hay imágenes de ejemplo disponibles.</p>';
        }

        this.architectureElement.textContent = project.detailedArchitecture || 'Arquitectura modular basada en microservicios.';
        this.techStackContainer.innerHTML = project.tech_stack
            .map(tech => `<span class="px-2 py-1 bg-gray-900 border border-gray-700 rounded text-xs text-gray-400">${tech}</span>`)
            .join('');

        this.modal.classList.remove('hidden');
    }
}
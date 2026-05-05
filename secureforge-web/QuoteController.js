/**
 * SecureForge Systems - QuoteController
 * Controlador para manejar el formulario de generación de proyectos y solicitud de cotización.
 */
export default class QuoteController {
    constructor() {
        this.projectForm = document.getElementById('project-form');
        this.generateBtn = document.getElementById('generate-btn');
        this.briefingContent = document.getElementById('briefing-content');
        this.quoteActionContainer = document.getElementById('quote-action-container');
        this.sendQuoteBtn = document.getElementById('send-quote-btn');
    }

    init() {
        if (this.projectForm) {
            this.projectForm.addEventListener('submit', this.handleGenerateProject.bind(this));
        }
        if (this.sendQuoteBtn) {
            this.sendQuoteBtn.addEventListener('click', this.handleSendQuote.bind(this));
        }
    }

    async handleGenerateProject(event) {
        event.preventDefault();

        const name = document.getElementById('client-name').value.trim();
        const desc = document.getElementById('project-desc').value.trim();

        if (!desc) {
            alert('Por favor, redacta tu idea para procesar los requerimientos.');
            return;
        }

        this.briefingContent.innerHTML = '<p class="text-brand-green animate-pulse">> ESCANEANDO REQUERIMIENTOS...</p>';
        this.generateBtn.disabled = true;

        await new Promise(resolve => setTimeout(resolve, 1500)); // Simulate processing time

        this.briefingContent.innerHTML = `
            <div class="space-y-6">
                <p class="text-brand-green">*** ANÁLISIS DE PROYECTO FINALIZADO ***</p>
                <p class="text-white font-bold underline">ID_CLIENTE: ${name || 'ANÓNIMO'}</p>
                <div class="text-brand-light/80 italic">"${desc}"</div>
                <div class="p-4 bg-brand-green/5 border-l-2 border-brand-green">
                    <p class="text-white text-xs font-bold mb-2 uppercase">Hoja de Ruta Sugerida:</p>
                    <ul class="text-xs space-y-1 text-brand-green">
                        <li>> ARQUITECTURA: MVC Estructurado</li>
                        <li>> BASE DE DATOS: MySQL Normalizada</li>
                        <li>> SEGURIDAD: ForgeLock Core Auth</li>
                    </ul>
                </div>
                <p class="text-[10px] text-gray-500">SISTEMA LISTO PARA ASIGNACIÓN DE DESARROLLADOR_</p>
            </div>
        `;
        this.quoteActionContainer.classList.remove('hidden');
        this.generateBtn.disabled = false;
    }

    async handleSendQuote() {
        const name = document.getElementById('client-name').value.trim();
        const desc = document.getElementById('project-desc').value.trim();

        // TODO: Replace with actual fetch to your Java backend /api/quote-request endpoint
        // const response = await fetch('/api/quote-request', { ... });
        await new Promise(resolve => setTimeout(resolve, 1000)); // Simulate network delay
        alert('¡Solicitud de cotización enviada! Pronto nos contactaremos contigo.');
        this.quoteActionContainer.classList.add('hidden');
        this.projectForm.reset();
        this.briefingContent.innerHTML = '<p class="animate-pulse">> CONSOLA DE ANÁLISIS ESPERANDO DATOS...</p>';
    }
}
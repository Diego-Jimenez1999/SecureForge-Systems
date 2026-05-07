import PurchaseModal from './controllers/purchaseModal.js';
import ContactController from './controllers/ContactController.js';
import ProductController from './controllers/ProductController.js';
import ServiceDetailController from './controllers/ServiceDetailController.js';
import ProjectDetailController from './controllers/ProjectDetailController.js';
import AppConfig from './config/appConfig.js';

document.addEventListener('DOMContentLoaded', () => {
    // Inicializa el modal de compra
    new PurchaseModal();
    new ContactController().init();
    new ProductController().init();
    new ServiceDetailController().init();
    new ProjectDetailController().init();

    // Lógica para el formulario de cotización (GENERAR PROYECTO)
    const projectForm = document.getElementById('project-form');
    const generateBtn = document.getElementById('generate-btn');
    const briefingContent = document.getElementById('briefing-content');
    const quoteActionContainer = document.getElementById('quote-action-container');
    const sendQuoteBtn = document.getElementById('send-quote-btn');

    if (projectForm) {
        projectForm.addEventListener('submit', async (e) => {
            e.preventDefault();

            const clientName = document.getElementById('client-name').value.trim();
            const projectDesc = document.getElementById('project-desc').value.trim();

            if (!clientName || !projectDesc) {
                alert('Por favor, completa tu nombre y la descripción del proyecto.');
                return;
            }

            generateBtn.disabled = true;
            generateBtn.innerHTML = '<i class="ph ph-circle-notch animate-spin"></i> PROCESANDO...';
            briefingContent.innerHTML = '<p class="text-brand-green animate-pulse text-xs">> ANALIZANDO REQUERIMIENTOS...</p>';

            try {
                const response = await fetch(`${AppConfig.API_BASE_URL}/api/briefings`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ clientName, description: projectDesc }),
                });

                if (!response.ok) {
                    throw new Error(`Error al enviar cotización: ${response.status} ${response.statusText}`);
                }

                const result = await response.json();
                console.log('Cotización guardada:', result);

                briefingContent.innerHTML = `
                    <div class="space-y-6">
                        <p class="text-brand-green font-bold text-[10px] tracking-widest border-b border-gray-800 pb-2">*** INFORME DE ANÁLISIS PRELIMINAR ***</p>
                        <p class="text-white font-bold underline decoration-brand-green">CLIENTE: ${result.clientName.toUpperCase()}</p>
                        <div class="text-brand-light/60 text-[11px] leading-relaxed italic border-l border-gray-700 pl-4">"${result.description}"</div>
                        <p class="text-[10px] text-gray-500 animate-pulse font-bold tracking-widest uppercase">Análisis completado. Diego ha sido notificado y se comunicará contigo pronto.</p>
                    </div>
                `;
                quoteActionContainer.classList.remove('hidden');
                // Aquí podrías resetear el formulario si lo deseas
                // projectForm.reset();

            } catch (error) {
                console.error('Error al procesar la cotización:', error);
                briefingContent.innerHTML = `<p class="text-red-500">> ERROR: ${error.message}. Por favor, inténtalo de nuevo.</p>`;
                quoteActionContainer.classList.add('hidden');
            } finally {
                generateBtn.disabled = false;
                generateBtn.innerHTML = 'Procesar Requerimientos';
            }
        });
    }

    // Lógica para el botón "Enviar Solicitud de Cotización" (si es diferente a la del formulario)
    if (sendQuoteBtn) {
        sendQuoteBtn.addEventListener('click', () => {
            // Aquí podrías redirigir a la sección de contacto o abrir un modal de confirmación
            alert('Tu solicitud de cotización ha sido enviada. Nos pondremos en contacto contigo pronto.');
            window.location.href = '#contacto'; // Ejemplo de redirección
        });
    }

    // Lógica para cargar y mostrar recursos gratis (ejemplo)
    const freeResourcesContainer = document.getElementById('free-resources-container'); // Necesitarías añadir este ID en tu HTML

    if (freeResourcesContainer) {
        async function loadFreeResources() {
            try {
                const response = await fetch(`${AppConfig.API_BASE_URL}/api/resources`);
                if (!response.ok) {
                    throw new Error(`Error al cargar recursos: ${response.status} ${response.statusText}`);
                }
                const resources = await response.json();
                
                freeResourcesContainer.innerHTML = resources.map(resource => `
                    <div class="bg-brand-card p-6 rounded-xl border border-gray-800 hover:border-brand-green/50 transition-colors">
                        <h3 class="text-xl font-bold text-white mb-2">${resource.title}</h3>
                        <p class="text-brand-light/60 text-sm mb-4">${resource.description}</p>
                        <a href="${resource.downloadUrl}" target="_blank" class="btn-action px-4 py-2 rounded text-xs font-bold uppercase tracking-widest">Descargar</a>
                    </div>
                `).join('');
            } catch (error) {
                console.error('Error al cargar los recursos gratis:', error);
                freeResourcesContainer.innerHTML = '<p class="text-red-500">No se pudieron cargar los recursos. Intenta de nuevo más tarde.</p>';
            }
        }
        loadFreeResources();
    }
});

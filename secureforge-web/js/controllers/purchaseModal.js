import PurchaseService from '../services/purchaseService.js';

export default class PurchaseModal {
    constructor() {
        // Elementos del DOM
        this.modal = document.getElementById('purchase-flow-modal');
        this.form = document.getElementById('purchase-flow-form');
        this.fileInput = document.getElementById('purchase-file-input');
        this.fileLabel = document.getElementById('purchase-file-name');
        this.submitBtn = document.getElementById('purchase-submit-btn');
        this.successState = document.getElementById('purchase-success-state');
        
        // Campos de información de producto
        this.displayProductName = document.getElementById('display-product-name');
        this.displayProductPrice = document.getElementById('display-product-price');

        this.init();
    }

    init() {
        if (!this.modal) return;

        // Cierre de modal (Click fuera o botón X)
        this.modal.addEventListener('click', (e) => {
            if (e.target === this.modal || e.target.closest('.close-modal')) this.closeModal();
        });

        // Validación de archivo y actualización de etiqueta
        this.fileInput?.addEventListener('change', () => this.handleFileUpload());

        // Envío del formulario
        this.form?.addEventListener('submit', (e) => this.handleSubmit(e));

        // INTEGRACIÓN AUTOMÁTICA: Detecta botones "Adquirir" o "Comprar"
        document.addEventListener('click', (e) => {
            const btn = e.target.closest('button, a');
            if (!btn) return;
            
            const text = btn.innerText.toUpperCase();
            if (text.includes('ADQUIRIR') || text.includes('COMPRAR')) {
                // Evitamos conflictos con funciones inline existentes
                e.preventDefault();
                e.stopPropagation();

                // Extraer datos de la card contenedora
                const card = btn.closest('div'); 
                const name = card?.querySelector('h3')?.innerText || 'Producto';
                const price = card?.querySelector('span')?.innerText || 'Precio a convenir';
                
                this.openModal(name, price);
            }
        });
    }

    openModal(name, price) {
        this.displayProductName.innerText = name;
        this.displayProductPrice.innerText = price;
        this.modal.classList.remove('hidden');
        this.modal.classList.add('flex');
        document.body.style.overflow = 'hidden';
    }

    closeModal() {
        this.modal.classList.replace('flex', 'hidden');
        document.body.style.overflow = 'auto';
        setTimeout(() => this.resetUI(), 300);
    }

    handleFileUpload() {
        const file = this.fileInput.files[0];
        if (!file) return;

        const validTypes = ['image/jpeg', 'image/png', 'application/pdf'];
        if (!validTypes.includes(file.type)) {
            alert('Formato no permitido. Por favor sube JPG, PNG o PDF.');
            this.fileInput.value = '';
            this.fileLabel.innerText = 'Sin archivo seleccionado';
            return;
        }
        this.fileLabel.innerText = `Cargado: ${file.name}`;
    }

    async handleSubmit(e) {
        e.preventDefault();
        
        // Estado: Procesando
        this.submitBtn.disabled = true;
        this.submitBtn.innerHTML = '<i class="ph ph-circle-notch animate-spin"></i> PROCESANDO...';

        const formData = new FormData();
        // Recopila los datos del formulario para el DTO
        const paymentData = {
            productName: this.displayProductName.innerText,
            name: this.form.elements['name'].value,
            email: this.form.elements['email'].value,
            phone: this.form.elements['phone'].value,
        };
        formData.append('paymentData', new Blob([JSON.stringify(paymentData)], { type: 'application/json' }));
        
        // Añade el archivo si existe
        if (this.fileInput.files.length > 0) {
            formData.append('receipt', this.fileInput.files[0]);
        }

        try {
            await PurchaseService.submitPurchase(formData);
            // Estado: Éxito
            this.form.classList.add('hidden');
            this.successState.classList.remove('hidden');
            setTimeout(() => this.closeModal(), 4000);
        } catch (error) {
            alert('Hubo un error al procesar tu solicitud. Intenta de nuevo.');
            this.submitBtn.disabled = false;
            this.submitBtn.innerText = 'CONFIRMAR COMPRA';
        }
    }

    resetUI() {
        this.form.reset();
        this.form.classList.remove('hidden');
        this.successState.classList.add('hidden');
        this.fileLabel.innerText = 'Subir comprobante';
        this.submitBtn.disabled = false;
        this.submitBtn.innerText = 'CONFIRMAR COMPRA';
    }
}
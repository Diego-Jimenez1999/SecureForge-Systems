/**
 * Controlador para manejar el flujo de compra de productos.
 */
export default class PurchaseController {
    constructor() {
        this.purchaseModal = document.getElementById('purchase-modal');
        this.closePurchaseModalBtn = document.getElementById('close-purchase-modal');
        this.purchaseForm = document.getElementById('purchase-form');
        this.submitPurchaseBtn = document.getElementById('submit-purchase-btn');
        this.purchaseProductNameSpan = document.getElementById('purchase-product-name');
        this.purchaseHiddenProductName = document.getElementById('purchase-hidden-product-name');
        this.purchaseHiddenProductPrice = document.getElementById('purchase-hidden-product-price');
        this.purchaseSuccessMessage = document.getElementById('purchase-success-message');

        this.nequiPaymentModal = document.getElementById('nequi-payment-modal'); // Keep original Nequi modal
        this.closeNequiModalBtn = document.getElementById('close-modal');
        this.confirmNequiPayBtn = document.getElementById('confirm-pay');
        this.targetName = document.getElementById('target-name');
        this.targetPrice = document.getElementById('target-price');
    }

    init() {
        window.addEventListener('secureforge:checkout', this.openPurchaseModal.bind(this));
        if (this.closePurchaseModalBtn) {
            this.closePurchaseModalBtn.addEventListener('click', this.closeModal.bind(this, this.purchaseModal));
        }
        if (this.purchaseForm) {
            this.purchaseForm.addEventListener('submit', this.handleSubmitPurchase.bind(this));
        }

        // Original Nequi modal logic
        if (this.closeNequiModalBtn) {
            this.closeNequiModalBtn.addEventListener('click', this.closeModal.bind(this, this.nequiPaymentModal));
        }
        if (this.confirmNequiPayBtn) {
            this.confirmNequiPayBtn.addEventListener('click', this.handleConfirmNequiPay.bind(this));
        }
    }

    openPurchaseModal(event) {
        const { name, price } = event.detail;
        this.purchaseProductNameSpan.textContent = name;
        this.purchaseHiddenProductName.value = name;
        this.purchaseHiddenProductPrice.value = price; // Store as string, convert to number on backend

        this.purchaseModal.classList.replace('hidden', 'flex');
        document.body.style.overflow = 'hidden';
        this.purchaseSuccessMessage.classList.add('hidden'); // Reset success message
        this.purchaseForm.classList.remove('hidden'); // Show form
    }

    closeModal(modalElement) {
        modalElement.classList.replace('flex', 'hidden');
        document.body.style.overflow = 'auto';
    }

    async handleSubmitPurchase(event) {
        event.preventDefault();

        const productName = this.purchaseHiddenProductName.value;
        const productPrice = this.purchaseHiddenProductPrice.value;
        const clientName = document.getElementById('purchase-client-name').value.trim();
        const clientEmail = document.getElementById('purchase-client-email').value.trim();
        const clientPhone = document.getElementById('purchase-client-phone').value.trim();
        const comprobanteFile = document.getElementById('purchase-comprobante').files[0];

        if (!clientName || !clientEmail || !clientPhone) {
            alert('Por favor, rellena todos los campos obligatorios.');
            return;
        }

        this.submitPurchaseBtn.innerHTML = '<i class="ph ph-circle-notch animate-spin"></i> Procesando...';
        this.submitPurchaseBtn.disabled = true;

        const formData = new FormData();
        formData.append('productName', productName);
        formData.append('productPrice', productPrice.replace(/[^0-9.-]+/g,"")); // Clean price string
        formData.append('clientName', clientName);
        formData.append('clientEmail', clientEmail);
        formData.append('clientPhone', clientPhone);
        if (comprobanteFile) {
            formData.append('comprobante', comprobanteFile);
        }

        // TODO: Replace with actual fetch to your Java backend /api/purchase endpoint
        // const response = await fetch('/api/purchase', { method: 'POST', body: formData });
        // if (!response.ok) throw new Error('Error al procesar la compra');
        await new Promise(resolve => setTimeout(resolve, 2000)); // Simulate network delay

        this.purchaseForm.classList.add('hidden');
        this.purchaseSuccessMessage.classList.remove('hidden');
        this.submitPurchaseBtn.disabled = false; // Re-enable for potential re-submission if form was not hidden
    }

    handleConfirmNequiPay() {
        alert('Pago registrado. Envíanos tu comprobante por WhatsApp al +57 304 3042589 para activar el servicio.');
        this.closeModal(this.nequiPaymentModal);
    }
}
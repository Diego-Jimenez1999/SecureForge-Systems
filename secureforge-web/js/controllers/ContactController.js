import AppConfig from '../config/appConfig.js';

/**
 * Controlador para manejar el formulario de contacto.
 * Ubicado en: js/controllers/ContactController.js
 */
export default class ContactController {
    constructor() {
        this.form = document.getElementById('contact-form');
        this.sendBtn = document.getElementById('send-contact-btn');
        this.recipientEmail = 'diego.jimez22@gmail.com';
    }

    init() {
        if (this.form) {
            this.form.addEventListener('submit', this.handleSubmit.bind(this));
        }
    }

    async handleSubmit(event) {
        event.preventDefault();

        const name = document.getElementById('contact-name').value.trim();
        const email = document.getElementById('contact-email').value.trim();
        const subject = document.getElementById('contact-subject').value.trim();
        const message = document.getElementById('contact-message').value.trim();

        if (!name || !email || !subject || !message) {
            alert('Por favor, rellena todos los campos.');
            return;
        }

        if (!this.isValidEmail(email)) {
            alert('Por favor, introduce un correo electrónico válido.');
            return;
        }

        this.sendBtn.innerHTML = '<i class="ph ph-circle-notch animate-spin"></i> Enviando...';
        this.sendBtn.disabled = true;

        try {
            const response = await fetch(`${AppConfig.API_BASE_URL}/api/contact`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, subject, message })
            });
            if (!response.ok) {
                throw new Error('No se pudo enviar el mensaje');
            }
            alert('¡Mensaje enviado con éxito!');
            this.form.reset();
        } catch (error) {
            console.error('Error:', error);
            alert('Hubo un error al enviar tu mensaje.');
        } finally {
            this.sendBtn.innerHTML = '<i class="ph ph-paper-plane-tilt"></i> Enviar Mensaje a Diego';
            this.sendBtn.disabled = false;
        }
    }

    isValidEmail(email) {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }
}

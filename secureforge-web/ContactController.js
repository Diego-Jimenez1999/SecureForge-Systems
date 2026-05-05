/**
 * Controlador para manejar el formulario de contacto.
 */
export default class ContactController {
    constructor() {
        this.form = document.getElementById('contact-form');
        this.sendBtn = document.getElementById('send-contact-btn');
        this.recipientEmail = 'diego.jimez22@gmail.com'; // Hardcoded for mailto, ideally from config
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
            alert('Por favor, rellena todos los campos para poder enviar tu mensaje.');
            return;
        }

        // Frontend validation (basic)
        if (!this.isValidEmail(email)) {
            alert('Por favor, introduce un correo electrónico válido.');
            return;
        }

        // Simulate sending to backend (replace with actual fetch to your Java backend)
        this.sendBtn.innerHTML = '<i class="ph ph-circle-notch animate-spin"></i> Enviando...';
        this.sendBtn.disabled = true;

        try {
            // TODO: Replace with actual fetch to your Java backend /api/contact endpoint
            // const response = await fetch('/api/contact', {
            //     method: 'POST',
            //     headers: { 'Content-Type': 'application/json' },
            //     body: JSON.stringify({ name, email, subject, message })
            // });
            // if (!response.ok) throw new Error('Error al enviar mensaje');
            await new Promise(resolve => setTimeout(resolve, 1500)); // Simulate network delay
            alert('¡Mensaje enviado con éxito! Pronto nos pondremos en contacto contigo.');
            this.form.reset();
        } catch (error) {
            console.error('Error al enviar el mensaje de contacto:', error);
            alert('Hubo un error al enviar tu mensaje. Por favor, inténtalo de nuevo más tarde.');
        } finally {
            this.sendBtn.innerHTML = '<i class="ph ph-paper-plane-tilt group-hover:translate-x-1 group-hover:-translate-y-1 transition-transform"></i> Enviar Mensaje a Diego';
            this.sendBtn.disabled = false;
        }
    }

    isValidEmail(email) {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }
}
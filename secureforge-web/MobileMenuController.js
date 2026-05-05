/**
 * Controlador para manejar la visibilidad del menú móvil.
 */
export default class MobileMenuController {
    constructor() {
        this.mobileMenuBtn = document.getElementById('mobile-menu-btn');
        this.mobileMenu = document.getElementById('mobile-menu');
        this.navLinks = this.mobileMenu ? this.mobileMenu.querySelectorAll('a') : [];
    }

    init() {
        if (this.mobileMenuBtn && this.mobileMenu) {
            this.mobileMenuBtn.addEventListener('click', this.toggleMobileMenu.bind(this));
            this.navLinks.forEach(link => {
                link.addEventListener('click', () => this.mobileMenu.classList.add('hidden'));
            });
        }
    }

    toggleMobileMenu() {
        this.mobileMenu.classList.toggle('hidden');
        document.body.style.overflow = this.mobileMenu.classList.contains('hidden') ? 'auto' : 'hidden';
    }
}
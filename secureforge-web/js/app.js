import ProductController from './controllers/ProductController.js';

/**
 * Punto de entrada principal de SecureForge Systems.
 */
document.addEventListener('DOMContentLoaded', () => {
    console.log('SecureForge App: Bootstrapping iniciado.');
    
    const productCtrl = new ProductController();
    productCtrl.init();
});
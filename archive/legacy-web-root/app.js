import ProductController from './controllers/ProductController.js';
import ContactController from './ContactController.js';
import QuoteController from './QuoteController.js';
import PurchaseController from './PurchaseController.js';
import MobileMenuController from './MobileMenuController.js';
import ServiceDetailController from './ServiceDetailController.js';
import ProjectDetailController from './ProjectDetailController.js';

document.addEventListener('DOMContentLoaded', () => {
    console.log('SecureForge Systems: Inicializando aplicación...');

    new ProductController().init();
    new ContactController().init();
    new QuoteController().init();
    new PurchaseController().init();
    new MobileMenuController().init();
    new ServiceDetailController().init();
    new ProjectDetailController().init();
});
import ProductModel from '../models/ProductModel.js';
import ProductView from '../views/ProductView.js';

/**
 * Controlador que une el modelo y la vista de productos.
 */
export default class ProductController {
    constructor() {
        this.model = ProductModel;
        this.view = new ProductView();
    }

    init() {
        console.log('ProductController: Inicializando catálogo...');
        const data = this.model.getAll();
        this.view.render(data);
    }
}
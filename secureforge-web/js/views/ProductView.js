/**
 * Vista encargada de renderizar los productos en el DOM.
 */
export default class ProductView {
    constructor() {
        this.container = document.getElementById('products-container');
        this._initEventListeners();
    }

    _initEventListeners() {
        if (!this.container) return;
        
        this.container.addEventListener('click', (e) => {
            const button = e.target.closest('.btn-action');
            if (button) {
                const productName = button.getAttribute('data-name');
                const productPrice = button.getAttribute('data-price');
                this.handleAcquire(productName, productPrice);
            }
        });
    }

    handleAcquire(name, price) {
        window.dispatchEvent(new CustomEvent('secureforge:checkout', {
            detail: { name, price }
        }));
    }

    render(products) {
        if (!this.container) return;
        this.container.innerHTML = products.map(product => this._createCard(product)).join('');
    }

    _createCard(product) {
        const tags = product.tech_stack
            .map(tech => `<span class="px-2 py-1 bg-gray-900 border border-gray-700 rounded text-xs text-gray-400">${tech}</span>`)
            .join('');

        return `
            <article class="forge-card bg-brand-card rounded-xl overflow-hidden flex flex-col h-full">
                <div class="p-8 flex-1">
                    <div class="flex justify-between items-start mb-6">
                        <div class="w-12 h-12 bg-brand-green/10 rounded-lg flex items-center justify-center text-brand-green text-2xl">
                            <i class="ph ${product.icon}"></i>
                        </div>
                        <span class="px-3 py-1 bg-brand-dark border border-gray-700 rounded-full text-xs text-brand-green">
                            ${product.product_type}
                        </span>
                    </div>
                    <h3 class="text-2xl font-poppins font-semibold text-white mb-2">${product.title}</h3>
                    <p class="text-brand-light/60 text-sm mb-6 leading-relaxed">${product.description}</p>
                    <div class="flex flex-wrap gap-2 mb-4">${tags}</div>
                </div>
                <div class="bg-brand-dark/50 p-6 border-t border-gray-800 flex justify-between items-center">
                    <div class="flex flex-col">
                        <span class="text-[10px] text-gray-500 uppercase tracking-widest">Licencia</span>
                        <span class="font-mono text-white font-bold">$${product.price_cop.toLocaleString()} COP</span>
                    </div>
                    <button class="btn-action px-5 py-2 rounded text-xs font-bold uppercase tracking-widest" data-name="${product.title}" data-price="$${product.price_cop.toLocaleString()} COP">
                        Adquirir
                    </button>
                    <button class="btn-secondary px-5 py-2 rounded text-xs font-bold uppercase tracking-widest border border-brand-green text-brand-green btn-detail-project" data-id="${product.id}">
                        Ver Detalles
                    </button>
                </div>
            </article>
        `;
    }
}

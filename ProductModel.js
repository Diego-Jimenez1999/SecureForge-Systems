/**
 * Modelo de datos para los productos de SecureForge.
 */
export const productsData = [
    {
        id: 1,
        title: "SecureGuard CRM",
        type: "Software Enterprise",
        icon: "ph-shield-plus",
        description: "Sistema de gestión de clientes con encriptación de extremo a extremo y auditoría de accesos integrada.",
        techStack: ["ES6", "Node.js", "MVC"],
        price: "$499 USD",
        status: "Available"
    },
    {
        id: 2,
        title: "ForgeLock Auth",
        type: "Librería de Seguridad",
        icon: "ph-fingerprint",
        description: "Módulo de autenticación biométrica y MFA listo para integrar en cualquier plataforma web moderna.",
        techStack: ["Vanilla JS", "WebAuthn"],
        price: "$150 USD",
        status: "LTS"
    }
];

export default class ProductModel {
    static getAll() {
        return [...productsData];
    }
}
// Nuevo requerimiento 3: inicialización del catálogo desde los datos existentes del proyecto.
package ms_producto.EA3Fullstack;

import ms_producto.EA3Fullstack.model.Producto;
import ms_producto.EA3Fullstack.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Ea3FullstackApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ea3FullstackApplication.class, args);
    }

    // carga el catálogo una sola vez si PostgreSQL está vacío.
    @Bean
    CommandLineRunner cargarCatalogoInicial(ProductoRepository productoRepository) {
        return args -> {
        if (!productoRepository.existsByCodigo("MC001")) {
            saveProduct(productoRepository, "MC001", "Mat. Construcción", "Cementos", "Cemento Polpaico gris 25 kg", "Polpaico", "Saco", 3200, 5990, 80, 20);
        }
        if (!productoRepository.existsByCodigo("MC002")) {
            saveProduct(productoRepository, "MC002", "Mat. Construcción", "Cementos", "Cemento Melón blanco 25 kg", "Melón", "Saco", 4100, 7490, 40, 10);
        }
        if (!productoRepository.existsByCodigo("MC003")) {
            saveProduct(productoRepository, "MC003", "Mat. Construcción", "Morteros", "Mortero cola cerámica 25 kg", "Volcán", "Saco", 2800, 5200, 50, 15);
        }
        if (!productoRepository.existsByCodigo("MC004")) {
            saveProduct(productoRepository, "MC004", "Mat. Construcción", "Morteros", "Mortero nivelador piso 25 kg", "Weber", "Saco", 3500, 6490, 30, 10);
        }
        if (!productoRepository.existsByCodigo("MC005")) {
            saveProduct(productoRepository, "MC005", "Mat. Construcción", "Áridos", "Arena fina construcción 25 kg", "Granel", "Saco", 800, 1800, 60, 20);
        }
        if (!productoRepository.existsByCodigo("MC006")) {
            saveProduct(productoRepository, "MC006", "Mat. Construcción", "Áridos", "Ripio 25 kg", "Granel", "Saco", 700, 1500, 60, 20);
        }
        if (!productoRepository.existsByCodigo("MC007")) {
            saveProduct(productoRepository, "MC007", "Mat. Construcción", "Ladrillos", "Ladrillo fiscal N°5", "Local", "Unidad", 190, 380, 500, 100);
        }
        if (!productoRepository.existsByCodigo("MC008")) {
            saveProduct(productoRepository, "MC008", "Mat. Construcción", "Ladrillos", "Ladrillo prensado 6x14x29 cm", "Melón", "Unidad", 280, 550, 300, 80);
        }
        if (!productoRepository.existsByCodigo("MC009")) {
            saveProduct(productoRepository, "MC009", "Mat. Construcción", "Bloques", "Bloque de hormigón 19x19x39 cm", "Volcán", "Unidad", 620, 1200, 200, 50);
        }
        if (!productoRepository.existsByCodigo("MC010")) {
            saveProduct(productoRepository, "MC010", "Mat. Construcción", "Bloques", "Bloque liviano 10x20x40 cm", "Ytong", "Unidad", 890, 1690, 100, 30);
        }
        if (!productoRepository.existsByCodigo("PT001")) {
            saveProduct(productoRepository, "PT001", "Pinturas", "Látex", "Pintura látex interior 1 galón blanco", "Sipa", "Galón", 5200, 9990, 40, 10);
        }
        if (!productoRepository.existsByCodigo("PT002")) {
            saveProduct(productoRepository, "PT002", "Pinturas", "Látex", "Pintura látex interior 4 litros (varios col.)", "Sipa", "Envase 4L", 6800, 12990, 30, 8);
        }
        if (!productoRepository.existsByCodigo("PT003")) {
            saveProduct(productoRepository, "PT003", "Pinturas", "Látex", "Pintura látex exterior 1 galón blanco", "Kömex", "Galón", 7200, 13990, 25, 8);
        }
        if (!productoRepository.existsByCodigo("PT004")) {
            saveProduct(productoRepository, "PT004", "Pinturas", "Esmalte", "Esmalte sintético 1/4 litro (varios col.)", "Sipa", "1/4 L", 2100, 4290, 50, 15);
        }
        if (!productoRepository.existsByCodigo("PT005")) {
            saveProduct(productoRepository, "PT005", "Pinturas", "Esmalte", "Esmalte sintético 1 litro (varios col.)", "Sipa", "1 litro", 4800, 9490, 30, 10);
        }
        if (!productoRepository.existsByCodigo("PT006")) {
            saveProduct(productoRepository, "PT006", "Pinturas", "Antihumedad", "Pintura antihumedad 1 galón blanco", "Kömex", "Galón", 9500, 17990, 15, 5);
        }
        if (!productoRepository.existsByCodigo("PT007")) {
            saveProduct(productoRepository, "PT007", "Pinturas", "Accesorios", "Rodillo lana 23 cm con mango", "Wurth", "Unidad", 1900, 3990, 30, 10);
        }
        if (!productoRepository.existsByCodigo("PT008")) {
            saveProduct(productoRepository, "PT008", "Pinturas", "Accesorios", "Brocha cerda natural 3\"", "Sipa", "Unidad", 800, 1690, 40, 10);
        }
        if (!productoRepository.existsByCodigo("PT009")) {
            saveProduct(productoRepository, "PT009", "Pinturas", "Accesorios", "Cinta de enmascarar 24mm x 50m", "3M", "Rollo", 1200, 2490, 60, 20);
        }
        if (!productoRepository.existsByCodigo("HM001")) {
            saveProduct(productoRepository, "HM001", "Herramientas", "Manuales", "Martillo carpintero 500g", "Stanley", "Unidad", 4200, 7990, 20, 5);
        }
        if (!productoRepository.existsByCodigo("HM002")) {
            saveProduct(productoRepository, "HM002", "Herramientas", "Manuales", "Alicate universal 8\"", "Stanley", "Unidad", 3800, 7290, 15, 5);
        }
        if (!productoRepository.existsByCodigo("HM003")) {
            saveProduct(productoRepository, "HM003", "Herramientas", "Manuales", "Destornillador plano 6x100mm", "Stanley", "Unidad", 900, 1990, 30, 10);
        }
        if (!productoRepository.existsByCodigo("HM004")) {
            saveProduct(productoRepository, "HM004", "Herramientas", "Manuales", "Destornillador Phillips PH2 6x100mm", "Stanley", "Unidad", 900, 1990, 30, 10);
        }
        if (!productoRepository.existsByCodigo("HM005")) {
            saveProduct(productoRepository, "HM005", "Herramientas", "Manuales", "Llave ajustable 10\"", "Bahco", "Unidad", 4500, 8490, 12, 4);
        }
        if (!productoRepository.existsByCodigo("HM006")) {
            saveProduct(productoRepository, "HM006", "Herramientas", "Manuales", "Juego llaves hexagonales métrico x9", "Stanley", "Set", 2800, 5490, 15, 5);
        }
        if (!productoRepository.existsByCodigo("HM007")) {
            saveProduct(productoRepository, "HM007", "Herramientas", "Manuales", "Serrucho 20\" 7 dientes por pulgada", "Stanley", "Unidad", 4900, 9490, 10, 3);
        }
        if (!productoRepository.existsByCodigo("HM008")) {
            saveProduct(productoRepository, "HM008", "Herramientas", "Manuales", "Nivel de burbuja 60 cm", "Stanley", "Unidad", 5500, 10490, 8, 3);
        }
        if (!productoRepository.existsByCodigo("HM009")) {
            saveProduct(productoRepository, "HM009", "Herramientas", "Manuales", "Metro de tela 5m", "Stanley", "Unidad", 2200, 4290, 25, 8);
        }
        if (!productoRepository.existsByCodigo("HM010")) {
            saveProduct(productoRepository, "HM010", "Herramientas", "Manuales", "Cinta métrica 8m autoblocante", "Stanley", "Unidad", 3200, 6490, 20, 6);
        }
        if (!productoRepository.existsByCodigo("HE001")) {
            saveProduct(productoRepository, "HE001", "Herramientas", "Eléctricas", "Taladro percutor 650W 13mm", "Makita", "Unidad", 42000, 79990, 8, 2);
        }
        if (!productoRepository.existsByCodigo("HE002")) {
            saveProduct(productoRepository, "HE002", "Herramientas", "Eléctricas", "Atornillador inalámbrico 12V (kit)", "Makita", "Unidad", 55000, 104990, 5, 2);
        }
        if (!productoRepository.existsByCodigo("HE003")) {
            saveProduct(productoRepository, "HE003", "Herramientas", "Eléctricas", "Amoladora angular 4.5\" 800W", "Makita", "Unidad", 28000, 54990, 8, 2);
        }
        if (!productoRepository.existsByCodigo("HE004")) {
            saveProduct(productoRepository, "HE004", "Herramientas", "Eléctricas", "Sierra circular 7-1/4\" 1200W", "Skil", "Unidad", 38000, 72990, 4, 1);
        }
        if (!productoRepository.existsByCodigo("HE005")) {
            saveProduct(productoRepository, "HE005", "Herramientas", "Eléctricas", "Lijadora orbital 180W", "Black+Decker", "Unidad", 18000, 34990, 6, 2);
        }
        if (!productoRepository.existsByCodigo("HE006")) {
            saveProduct(productoRepository, "HE006", "Herramientas", "Eléctricas", "Caladora 500W", "Skil", "Unidad", 22000, 42990, 4, 1);
        }
        if (!productoRepository.existsByCodigo("GS001")) {
            saveProduct(productoRepository, "GS001", "Gasfitería", "Tuberías", "Cañería PVC 1/2\" x 6m", "Tigre", "Unidad", 2800, 5490, 30, 10);
        }
        if (!productoRepository.existsByCodigo("GS002")) {
            saveProduct(productoRepository, "GS002", "Gasfitería", "Tuberías", "Cañería PVC 3/4\" x 6m", "Tigre", "Unidad", 3900, 7490, 25, 8);
        }
        if (!productoRepository.existsByCodigo("GS003")) {
            saveProduct(productoRepository, "GS003", "Gasfitería", "Tuberías", "Cañería cobre 1/2\" x 5m", "Codelco", "Unidad", 9500, 17990, 15, 5);
        }
        if (!productoRepository.existsByCodigo("GS004")) {
            saveProduct(productoRepository, "GS004", "Gasfitería", "Uniones", "Codo PVC 1/2\" 90°", "Tigre", "Unidad", 180, 390, 100, 30);
        }
        if (!productoRepository.existsByCodigo("GS005")) {
            saveProduct(productoRepository, "GS005", "Gasfitería", "Uniones", "Te PVC 1/2\"", "Tigre", "Unidad", 220, 450, 80, 25);
        }
        if (!productoRepository.existsByCodigo("GS006")) {
            saveProduct(productoRepository, "GS006", "Gasfitería", "Uniones", "Unión doble PVC 1/2\"", "Tigre", "Unidad", 150, 320, 80, 25);
        }
        if (!productoRepository.existsByCodigo("GS007")) {
            saveProduct(productoRepository, "GS007", "Gasfitería", "Llaves", "Llave de paso esfera 1/2\" latón", "Emmeti", "Unidad", 1800, 3490, 30, 10);
        }
        if (!productoRepository.existsByCodigo("GS008")) {
            saveProduct(productoRepository, "GS008", "Gasfitería", "Llaves", "Llave de paso esfera 3/4\" latón", "Emmeti", "Unidad", 2500, 4990, 20, 8);
        }
        if (!productoRepository.existsByCodigo("GS009")) {
            saveProduct(productoRepository, "GS009", "Gasfitería", "Grifería", "Grifería lavamanos monocomando cromo", "Corona", "Unidad", 12000, 22990, 10, 3);
        }
        if (!productoRepository.existsByCodigo("GS010")) {
            saveProduct(productoRepository, "GS010", "Gasfitería", "Grifería", "Grifería cocina monocomando cuello alto", "Corona", "Unidad", 15000, 28990, 8, 2);
        }
        if (!productoRepository.existsByCodigo("GS011")) {
            saveProduct(productoRepository, "GS011", "Gasfitería", "Sellos", "Teflón 3/4\" x 12m", "3M", "Rollo", 350, 790, 80, 25);
        }
        if (!productoRepository.existsByCodigo("GS012")) {
            saveProduct(productoRepository, "GS012", "Gasfitería", "Sellos", "Silicona transparente 280ml", "Wacker", "Cartucho", 2800, 5490, 25, 8);
        }
        if (!productoRepository.existsByCodigo("EL001")) {
            saveProduct(productoRepository, "EL001", "Electricidad", "Conductores", "Cable unipolar 1.5mm² (por metro)", "Condulac", "Metro", 290, 590, 100, 30);
        }
        if (!productoRepository.existsByCodigo("EL002")) {
            saveProduct(productoRepository, "EL002", "Electricidad", "Conductores", "Cable unipolar 2.5mm² (por metro)", "Condulac", "Metro", 390, 790, 100, 30);
        }
        if (!productoRepository.existsByCodigo("EL003")) {
            saveProduct(productoRepository, "EL003", "Electricidad", "Conductores", "Cable dúplex paralelo 2x1.5mm² (por metro)", "Condulac", "Metro", 490, 990, 80, 25);
        }
        if (!productoRepository.existsByCodigo("EL004")) {
            saveProduct(productoRepository, "EL004", "Electricidad", "Enchufes", "Enchufe empotrar 16A c/tierra (schuko)", "Bticino", "Unidad", 1900, 3690, 50, 15);
        }
        if (!productoRepository.existsByCodigo("EL005")) {
            saveProduct(productoRepository, "EL005", "Electricidad", "Enchufes", "Enchufe doble empotrar 16A c/tierra", "Bticino", "Unidad", 2800, 5490, 40, 12);
        }
        if (!productoRepository.existsByCodigo("EL006")) {
            saveProduct(productoRepository, "EL006", "Electricidad", "Interruptores", "Interruptor simple empotrar", "Bticino", "Unidad", 1600, 3190, 50, 15);
        }
        if (!productoRepository.existsByCodigo("EL007")) {
            saveProduct(productoRepository, "EL007", "Electricidad", "Interruptores", "Interruptor doble empotrar", "Bticino", "Unidad", 2200, 4290, 35, 10);
        }
        if (!productoRepository.existsByCodigo("EL008")) {
            saveProduct(productoRepository, "EL008", "Electricidad", "Tableros", "Tablero eléctrico 4 espacios DIN", "Legrand", "Unidad", 12000, 22990, 8, 2);
        }
        if (!productoRepository.existsByCodigo("EL009")) {
            saveProduct(productoRepository, "EL009", "Electricidad", "Tableros", "Disyuntor termomagnético 16A unipolar", "Schneider", "Unidad", 3200, 6490, 20, 5);
        }
        if (!productoRepository.existsByCodigo("EL010")) {
            saveProduct(productoRepository, "EL010", "Electricidad", "Tableros", "Disyuntor termomagnético 25A unipolar", "Schneider", "Unidad", 3500, 6990, 20, 5);
        }
        if (!productoRepository.existsByCodigo("EL011")) {
            saveProduct(productoRepository, "EL011", "Electricidad", "Iluminación", "Ampolleta LED 9W E27 luz fría", "Philips", "Unidad", 1900, 3990, 60, 20);
        }
        if (!productoRepository.existsByCodigo("EL012")) {
            saveProduct(productoRepository, "EL012", "Electricidad", "Iluminación", "Ampolleta LED 12W E27 luz cálida", "Philips", "Unidad", 2200, 4490, 50, 15);
        }
        if (!productoRepository.existsByCodigo("EL013")) {
            saveProduct(productoRepository, "EL013", "Electricidad", "Iluminación", "Panel LED empotrar 18W 22cm", "Ledvance", "Unidad", 5800, 11490, 20, 6);
        }
        if (!productoRepository.existsByCodigo("TR001")) {
            saveProduct(productoRepository, "TR001", "Tornillería", "Tornillos", "Tornillo autoperf. 8x1\" caja 100 unid.", "Hilti", "Caja", 1500, 2990, 40, 12);
        }
        if (!productoRepository.existsByCodigo("TR002")) {
            saveProduct(productoRepository, "TR002", "Tornillería", "Tornillos", "Tornillo madera 4x40mm caja 100 unid.", "Hilti", "Caja", 1200, 2490, 40, 12);
        }
        if (!productoRepository.existsByCodigo("TR003")) {
            saveProduct(productoRepository, "TR003", "Tornillería", "Tornillos", "Tornillo volcanita 3.5x25mm caja 200 unid.", "Hilti", "Caja", 1800, 3490, 30, 10);
        }
        if (!productoRepository.existsByCodigo("TR004")) {
            saveProduct(productoRepository, "TR004", "Tornillería", "Tacos", "Taco fisher S6 bolsa 100 unid.", "Fischer", "Bolsa", 1600, 3190, 35, 10);
        }
        if (!productoRepository.existsByCodigo("TR005")) {
            saveProduct(productoRepository, "TR005", "Tornillería", "Tacos", "Taco fisher S8 bolsa 50 unid.", "Fischer", "Bolsa", 1400, 2890, 30, 10);
        }
        if (!productoRepository.existsByCodigo("TR006")) {
            saveProduct(productoRepository, "TR006", "Tornillería", "Pernos", "Perno hex. 3/8\" x 2\" c/tuerca y golilla", "Granel", "Unidad", 120, 290, 200, 50);
        }
        if (!productoRepository.existsByCodigo("TR007")) {
            saveProduct(productoRepository, "TR007", "Tornillería", "Anclajes", "Perno de anclaje 10x100mm", "Hilti", "Unidad", 480, 990, 60, 15);
        }
        if (!productoRepository.existsByCodigo("TR008")) {
            saveProduct(productoRepository, "TR008", "Tornillería", "Anclajes", "Anclaje químico epoxi 300ml", "Fischer", "Cartucho", 8500, 15990, 8, 2);
        }
        if (!productoRepository.existsByCodigo("MD001")) {
            saveProduct(productoRepository, "MD001", "Madera", "Pino cepillado", "Pino cepillado 1x3\" x 3m", "Local", "Unidad", 2100, 4290, 40, 12);
        }
        if (!productoRepository.existsByCodigo("MD002")) {
            saveProduct(productoRepository, "MD002", "Madera", "Pino cepillado", "Pino cepillado 2x4\" x 3m", "Local", "Unidad", 3800, 7490, 30, 10);
        }
        if (!productoRepository.existsByCodigo("MD003")) {
            saveProduct(productoRepository, "MD003", "Madera", "Tableros", "Terciado estructural 18mm 1.22x2.44m", "Arauco", "Plancha", 18000, 34990, 20, 5);
        }
        if (!productoRepository.existsByCodigo("MD004")) {
            saveProduct(productoRepository, "MD004", "Madera", "Tableros", "MDF 15mm 1.22x2.44m", "Arauco", "Plancha", 14000, 27990, 15, 4);
        }
        if (!productoRepository.existsByCodigo("MD005")) {
            saveProduct(productoRepository, "MD005", "Madera", "Tableros", "OSB 9mm 1.22x2.44m", "Arauco", "Plancha", 9500, 18990, 18, 5);
        }
        if (!productoRepository.existsByCodigo("MD006")) {
            saveProduct(productoRepository, "MD006", "Madera", "Tableros", "Volcanita estándar 10mm 1.2x2.4m", "Volcán", "Plancha", 4500, 8990, 30, 8);
        }
        if (!productoRepository.existsByCodigo("MD007")) {
            saveProduct(productoRepository, "MD007", "Madera", "Tableros", "Volcanita resistente humedad 10mm", "Volcán", "Plancha", 6200, 11990, 15, 4);
        }
        if (!productoRepository.existsByCodigo("SE001")) {
            saveProduct(productoRepository, "SE001", "Seguridad", "EPP", "Casco seguridad blanco", "3M", "Unidad", 3500, 6990, 15, 5);
        }
        if (!productoRepository.existsByCodigo("SE002")) {
            saveProduct(productoRepository, "SE002", "Seguridad", "EPP", "Guantes de cuero talla L", "3M", "Par", 1800, 3690, 20, 6);
        }
        if (!productoRepository.existsByCodigo("SE003")) {
            saveProduct(productoRepository, "SE003", "Seguridad", "EPP", "Antiparras de seguridad clear", "3M", "Unidad", 1200, 2490, 25, 8);
        }
        if (!productoRepository.existsByCodigo("SE004")) {
            saveProduct(productoRepository, "SE004", "Seguridad", "EPP", "Mascarilla respirador N95 (caja x10)", "3M", "Caja", 5500, 10990, 10, 3);
        }
        if (!productoRepository.existsByCodigo("SE005")) {
            saveProduct(productoRepository, "SE005", "Seguridad", "EPP", "Arnés de seguridad 1 punto", "3M", "Unidad", 18000, 34990, 4, 1);
        }
        if (!productoRepository.existsByCodigo("JA001")) {
            saveProduct(productoRepository, "JA001", "Jardín", "Mangueras", "Manguera riego 3/4\" x 25m", "Tigre", "Unidad", 12000, 22990, 6, 2);
        }
        if (!productoRepository.existsByCodigo("JA002")) {
            saveProduct(productoRepository, "JA002", "Jardín", "Mangueras", "Manguera expandible 30m", "Bestway", "Unidad", 15000, 28990, 5, 2);
        }
        if (!productoRepository.existsByCodigo("JA003")) {
            saveProduct(productoRepository, "JA003", "Jardín", "Mangueras", "Pistola de riego 8 modos", "Bestway", "Unidad", 2800, 5490, 10, 3);
        }
        if (!productoRepository.existsByCodigo("JA004")) {
            saveProduct(productoRepository, "JA004", "Jardín", "Herramientas", "Pala punta redonda #2 con mango", "Corona", "Unidad", 5500, 10990, 8, 2);
        }
        if (!productoRepository.existsByCodigo("JA005")) {
            saveProduct(productoRepository, "JA005", "Jardín", "Herramientas", "Rastrillo 16 dientes con mango", "Corona", "Unidad", 4800, 9490, 6, 2);
        }
        };
    }

    private static void saveProduct(ProductoRepository repository, String codigo, String categoria, String subcategoria, String nombre, String marca, String unidad, double precioCompra, double precioVenta, int stock, int stockMinimo) {
        Producto producto = new Producto();
        producto.setCodigo(codigo);
        producto.setCategoria(categoria);
        producto.setSubCategoria(subcategoria);
        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setUnidad(unidad);
        producto.setPrecioCompra(precioCompra);
        producto.setPrecioVenta(precioVenta);
        producto.setStock(stock);
        producto.setStockMinimo(stockMinimo);
        repository.save(producto);
    }
}

package br.com.ifsp.pi.lixt.statistics;

import br.com.ifsp.pi.lixt.data.business.product.Product;
import br.com.ifsp.pi.lixt.data.business.purchaselocal.PurchaseLocal;
import br.com.ifsp.pi.lixt.data.enumeration.MeasureType;
import br.com.ifsp.pi.lixt.dto.*;
import br.com.ifsp.pi.lixt.instantiator.ProductDtoInstantiator;
import br.com.ifsp.pi.lixt.mapper.ProductMapper;
import br.com.ifsp.pi.lixt.utils.database.operations.GeolocalizationConvert;
import org.locationtech.jts.io.ParseException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class DataForPurchaseLocalHistory {

    private static final int amountOfCategories = 3;
    private static final int amountOfLists = 1;
    private static final int amountOfProductsPerCategory = 5;

    public static List<CategoryDto> createCategories() {

        List<CategoryDto> categories = new ArrayList<>();

        for (int x=0; x<=amountOfCategories; x++) {
            String categoryName = "Categoria " + x;
            categories.add(
                    CategoryDto.builder()
                            .name(categoryName)
                            .build()
            );
        }

        return categories;
    }

    public static List<ListOfItemsDto> createListOfItems(Long userId) {

        List<ListOfItemsDto> listOfItemsDtos = new ArrayList<>();

        for (int x=0; x<=amountOfLists; x++) {
            String listName = "Lista " + x;
            listOfItemsDtos.add(
                    ListOfItemsDto.builder()
                            .description("Lista de compras usada para testes")
                            .id(null)
                            .listMembers(null)
                            .nameList(listName)
                            .owner(null)
                            .ownerId(userId)
                            .productsOfList(null)
                            .build()
            );
        }

        return listOfItemsDtos;
    }

    public static List<Product> createProducts(List<CategoryDto> categories) {

        List<Product> products = new ArrayList<>();

        for (CategoryDto category : categories) {
            for(int x=0; x<=amountOfProductsPerCategory; x++) {
                String productName = "Produto "+ x;
                products.add(
                        ProductDtoInstantiator.createProduct(
                                productName,
                                category,
                                MeasureType.UNITY,
                                5)
                );
            }
        }

        return products;
    }

    public static List<ProductOfListDto> createProductsOfList(List<Product> products,
                                                              List<ListOfItemsDto> listsOfItems) {

        List<ProductOfListDto> productsOfList = new ArrayList<>();

        for (ListOfItemsDto listOfItemsDto : listsOfItems) {
            for (Product product : products) {
                productsOfList.add(
                        ProductOfListDto.builder()
                                .plannedAmount(2)
                                .markedAmount(2)
                                .assignedUserId(null)
                                .amountComment(0)
                                .isMarked(true)
                                .listId(listOfItemsDto.getId())
                                .measureType(product.getMeasureType())
                                .measureValue(product.getMeasureValue())
                                .name(product.getName())
                                .product(null)
                                .userWhoMarkedId(null)
                                .productId(product.getId())
                                .product(ProductMapper.entityToDto(product))
                                .price(generatePrice())
                                .build()
                );
            }
        }
        return productsOfList;
    }

    public static List<PurchaseLocal> createLocals() throws ParseException {
        List<PurchaseLocal> purchaseLocals = new ArrayList<>();

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Hirota Food Express, R. Pedro de Toledo, 591, S??o Paulo, S??o Paulo 04039, Brazil")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.598575943903683,
                                -46.64251755477454)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Carrefour, Av. Paulo Faccini, 214, Guarulhos, S??o Paulo 07112, Brazil")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.468040803816848,
                                -46.521255499611186)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Dia, Guarulhos, S??o Paulo 07010, Brazil")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.461484034730404,
                                -46.52441078192038)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Lopes Supermercado")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.460238634256868,
                                -46.51902523958469)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Mota Supermercado")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.483770584005967,
                                -46.57289902605527)).build()
        );

        /*purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("P??o de A????car")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.602370992612208,
                                -46.63952485781894)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Sa??de Supermercados")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.604546508903454,
                                -46.63637119381647)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Ayumi Supermercado")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.656223265939616,
                                -46.65445275759117)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Mercadinho Nikkey")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.66629229497545,
                                -46.655413971474275)).build()
        );

        purchaseLocals.add(
                PurchaseLocal.builder()
                        .name("Club de Campo Supermercado, Av. Lico Maia, 930, Diadema, S??o Paulo 09980, Brazil")
                        .location(GeolocalizationConvert.convertCoordsToPoint(
                                -23.69944409364102,
                                -46.60914957493367)).build()
        );*/

        return purchaseLocals;
    }

    public static List<PurchaseDto> generatePurchases(List<Long> purchaseLocalIds,
                                                      List<ProductOfListDto> products,
                                                      List<ListOfItemsDto> lists,
                                                      Long userId) {
        List<PurchaseDto> purchases = new ArrayList<>();

        List<List<ProductOfListDto>> productsGroupedByList = new ArrayList<>();

        for (ListOfItemsDto list : lists) {
            productsGroupedByList.add(
                    products.stream().filter(e -> e.getListId() == list.getId())
                            .collect(Collectors.toList())
            );
        }

        LocalDateTime purchaseDate = LocalDateTime.of(
                2008,
                12,
                28,
                10,
                15,
                30);

        for (List<ProductOfListDto> list : productsGroupedByList) {
            BigDecimal purchasePrice = list.stream()
                    .map(e -> e.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            for (Long purchaseLocalId : purchaseLocalIds) {
                purchaseDate = purchaseDate.plusDays(3);
                purchases.add(
                    PurchaseDto.builder()
                            .purchaseLocalId(purchaseLocalId)
                            .purchasePrice(purchasePrice)
                            .purchaseDate(purchaseDate)
                            .purchaseLists(createPurchaseLists(list, purchasePrice))
                            .build()
                );
            }
        }

        return purchases;
    }

    public static List<PurchaseListDto> createPurchaseLists(List<ProductOfListDto> products,
                                                            BigDecimal purchasePrice) {
        List<PurchaseListDto> purchaseLists = new ArrayList<>();
        List<ItemOfPurchaseDto> items = new ArrayList<>();

        purchaseLists.add(
                PurchaseListDto.builder()
                        .listId(products.get(0).getListId())
                        .partialPurchasePrice(purchasePrice)
                        .build()
        );

        for(ProductOfListDto product : products)
            items.add(
                    ItemOfPurchaseDto.builder()
                            .productId(product.getProductId())
                            .productOfListId(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .measureType(product.getMeasureType())
                            .measureValue(product.getMeasureValue())
                            .build()
            );

        purchaseLists.get(0).setItemsOfPurchase(items);
        return purchaseLists;
    }

    public static BigDecimal generatePrice() {
        return new BigDecimal(BigInteger.valueOf((new Random().nextInt(9991)+10)), 2);
    }
}

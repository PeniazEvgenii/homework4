package ru.aston.hometask.discount;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.aston.hometask.discount.api.IUserDiscount;
import ru.aston.hometask.discount.decorators.AgeDiscountDecorator;
import ru.aston.hometask.discount.decorators.GenderDiscountDecorator;
import ru.aston.hometask.discount.decorators.NightRegistrationDiscount;
import ru.aston.hometask.discount.decorators.StandardUserDiscount;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DiscountDecoratorFactory {

    public static IUserDiscount buildDecoratorChain() {
        IUserDiscount decorator = new StandardUserDiscount();
        decorator = new AgeDiscountDecorator(decorator);
        decorator = new GenderDiscountDecorator(decorator);
        decorator = new NightRegistrationDiscount(decorator);

        return decorator;
    }
}

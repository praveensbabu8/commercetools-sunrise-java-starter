package controllers.shoppingcart;

import com.commercetools.sunrise.framework.CartFinder;
import com.commercetools.sunrise.framework.checkout.CheckoutStepControllerComponent;
import com.commercetools.sunrise.framework.checkout.confirmation.CheckoutConfirmationControllerAction;
import com.commercetools.sunrise.framework.checkout.confirmation.CheckoutConfirmationFormData;
import com.commercetools.sunrise.framework.checkout.confirmation.SunriseCheckoutConfirmationController;
import com.commercetools.sunrise.framework.checkout.confirmation.viewmodels.CheckoutConfirmationPageContentFactory;
import com.commercetools.sunrise.framework.components.controllers.RegisteredComponents;
import com.commercetools.sunrise.framework.controllers.cache.NoCache;
import com.commercetools.sunrise.framework.reverserouters.shoppingcart.cart.CartReverseRouter;
import com.commercetools.sunrise.framework.reverserouters.shoppingcart.checkout.CheckoutReverseRouter;
import com.commercetools.sunrise.framework.template.TemplateControllerComponentsSupplier;
import com.commercetools.sunrise.framework.template.engine.ContentRenderer;
import com.commercetools.sunrise.sessions.cart.CartOperationsControllerComponentSupplier;
import io.sphere.sdk.orders.Order;
import play.data.FormFactory;
import play.mvc.Result;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@NoCache
@RegisteredComponents({
        TemplateControllerComponentsSupplier.class,
        CheckoutStepControllerComponent.class,
        CartOperationsControllerComponentSupplier.class
})
public final class CheckoutConfirmationController extends SunriseCheckoutConfirmationController {

    private final CartReverseRouter cartReverseRouter;
    private final CheckoutReverseRouter checkoutReverseRouter;

    @Inject
    public CheckoutConfirmationController(final ContentRenderer contentRenderer,
                                          final FormFactory formFactory,
                                          final CheckoutConfirmationFormData formData,
                                          final CartFinder cartFinder,
                                          final CheckoutConfirmationControllerAction controllerAction,
                                          final CheckoutConfirmationPageContentFactory pageContentFactory,
                                          final CartReverseRouter cartReverseRouter,
                                          final CheckoutReverseRouter checkoutReverseRouter) {
        super(contentRenderer, formFactory, formData, cartFinder, controllerAction, pageContentFactory);
        this.cartReverseRouter = cartReverseRouter;
        this.checkoutReverseRouter = checkoutReverseRouter;
    }

    @Nullable
    @Override
    public String getTemplateName() {
        return "checkout-confirmation";
    }

    @Nullable
    @Override
    public String getCmsPageKey() {
        return "default";
    }

    @Override
    public CompletionStage<Result> handleNotFoundCart() {
        return redirectToCall(cartReverseRouter.cartDetailPageCall());
    }

    @Override
    public CompletionStage<Result> handleSuccessfulAction(final Order order, final CheckoutConfirmationFormData formData) {
        return redirectToCall(checkoutReverseRouter.checkoutThankYouPageCall());
    }
}

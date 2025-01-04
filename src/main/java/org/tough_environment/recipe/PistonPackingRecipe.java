package org.tough_environment.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PistonPackingRecipe implements Recipe<PackingRecipeInput> {
    protected final String group;
    protected final CraftingRecipeCategory category;
    final Ingredient ingredient;
    final int count;
    final Block output;
    protected final DefaultedList<ItemStack> drops;

    public PistonPackingRecipe(String group, CraftingRecipeCategory category, Ingredient ingredient, int count, Block output, List<ItemStack> drops) {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.count = count;
        this.output = output;
        this.drops = DefaultedList.copyOf(ItemStack.EMPTY, drops.toArray(new ItemStack[0]));
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.MOVING_PISTON);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean matches(PackingRecipeInput input, World world) {
        return this.ingredient.test(input.item().asItem().getDefaultStack().copyWithCount(count));
    }


    @Override
    public boolean fits(int width, int height) {
        return true;
    }



    public Block getOutput() {
        return output;
    }

    public DefaultedList<ItemStack> getDrops() {
        return DefaultedList.copyOf(ItemStack.EMPTY, drops.stream().map(ItemStack::copy).toList().toArray(new ItemStack[0]));
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
    }


    @Override
    public boolean isIgnoredInRecipeBook() {
        return Recipe.super.isIgnoredInRecipeBook();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public ItemStack craft(PackingRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return getResult(lookup);
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output.asItem().getDefaultStack();
    }

    public static class Type implements RecipeType<PistonPackingRecipe>
    {
        public static final Type INSTANCE = new Type();
        public static final String ID = "piston_packing";
    }

    public static class Serializer implements RecipeSerializer<PistonPackingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "piston_packing";


        protected static final MapCodec<PistonPackingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance->instance.group(
                        Codec.STRING.optionalFieldOf("group", "")
                                .forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC.fieldOf("category")
                                .orElse(CraftingRecipeCategory.MISC)
                                .forGetter(recipe -> recipe.category),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .fieldOf("ingredient")
                                .forGetter(recipe -> recipe.ingredient),
                        Codec.INT
                                .fieldOf("count")
                                .forGetter(recipe -> recipe.count),
                        Identifier.CODEC
                                .fieldOf("output")
                                .forGetter(recipe -> Registries.BLOCK.getId(recipe.output)),
                        ItemStack.VALIDATED_CODEC
                                .listOf()
                                .fieldOf("drops")
                                .forGetter(PistonPackingRecipe::getDrops)
                ).apply(instance, (group, category, ingredient, count, outputId, drops) -> new PistonPackingRecipe(group, category, ingredient, count, Registries.BLOCK.get(outputId), drops))
        );
        public static final PacketCodec<RegistryByteBuf, PistonPackingRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        public Serializer() {}

        @Override
        public MapCodec<PistonPackingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, PistonPackingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        public static PistonPackingRecipe read(RegistryByteBuf buf) {
            String group = buf.readString();
            CraftingRecipeCategory category = buf.readEnumConstant(CraftingRecipeCategory.class);
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            int count = buf.readInt();
            Block output = Registries.BLOCK.get(buf.readIdentifier());
            List<ItemStack> drops = ItemStack.LIST_PACKET_CODEC.decode(buf);
            return new PistonPackingRecipe(group, category, ingredient, count, output, drops);
        }

        public static void write(RegistryByteBuf buf, PistonPackingRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            Ingredient.PACKET_CODEC.encode(buf, recipe.ingredient);
            buf.writeInt(recipe.count);
            buf.writeIdentifier(Registries.BLOCK.getId(recipe.output));
            ItemStack.LIST_PACKET_CODEC.encode(buf, recipe.getDrops());
        }
    }

    public static class JsonBuilder implements CraftingRecipeJsonBuilder {
        protected CraftingRecipeCategory category = CraftingRecipeCategory.MISC;
        protected Ingredient ingredient;
        protected int count;
        protected Block output;
        protected String fromBlockName;
        protected DefaultedList<ItemStack> drops = DefaultedList.of();
        @Nullable
        protected String group;

        public static JsonBuilder create(ItemConvertible input, int count, Block output) {
            JsonBuilder obj = new JsonBuilder();
            obj.ingredient = Ingredient.ofItems(input);
            obj.count = count;
            obj.fromBlockName = Registries.BLOCK.getId(output).getPath();
            obj.output = output;
            return obj;
        }

        public static JsonBuilder create(TagKey<Item> inputTag, int count, Block output) {
            JsonBuilder obj = new JsonBuilder();
            obj.ingredient = Ingredient.fromTag(inputTag);
            obj.count = count;
            obj.fromBlockName = inputTag.id().getPath();

            obj.output = output;
            return obj;
        }

        public JsonBuilder category(CraftingRecipeCategory category) {
            this.category = category;
            return this;
        }

        public JsonBuilder drops(ItemStack... itemStacks) {
            this.drops.addAll(Arrays.asList(itemStacks));
            return this;
        }

        public JsonBuilder drops(Item item, int count) {
            return this.drops(new ItemStack(item, count));
        }

        public JsonBuilder drops(Item item) {
            return this.drops(item, 1);
        }

        public JsonBuilder result(ItemStack itemStack) {
            this.drops.add(itemStack);
            return this;
        }

        public JsonBuilder result(Item item, int count) {
            this.drops.add(new ItemStack(item, count));
            return this;
        }

        public JsonBuilder result(Item item) {
            return this.result(item, 1);
        }

        @Override
        public JsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
            return this;
        }

        @Override
        public JsonBuilder group(@Nullable String string) {
            this.group = string;
            return this;
        }

        protected boolean isDefaultRecipe;
        public JsonBuilder markDefault() {
            this.isDefaultRecipe = true;
            return this;
        }

        @Override
        public Item getOutputItem() {
            return output.asItem();
        }

        @Override
        public void offerTo(RecipeExporter exporter) {
            this.offerTo(exporter, "tough_environment:piston_packing_" + fromBlockName);
        }

        @Override
        public void offerTo(RecipeExporter exporter, Identifier recipeId) {
            Advancement.Builder advancementBuilder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
            PistonPackingRecipe turntableRecipe = new PistonPackingRecipe(
                    Objects.requireNonNullElse(this.group, ""),
                    this.category,
                    this.ingredient,
                    this.count,
                    this.output,
                    this.drops
            );

            exporter.accept(recipeId, turntableRecipe, advancementBuilder.build(recipeId.withPrefixedPath("recipes/" + this.category.asString() + "/")));
        }
    }
}
